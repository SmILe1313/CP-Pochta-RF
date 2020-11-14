<template>
	<div class="fullscreen">
    <div class="section">
      <h2> {{ filename }} </h2>
      <div class="chart">
        <span class="descr">Статистика по проверенным сведениям и результаты проверок</span>
        <span class="total"><h1>{{totalCount}}</h1> адресов</span>
        <vue-chartist :data="data" :options="options" type="Pie" :listener="listener"></vue-chartist>
        <div class="legend">
          <p v-for="(info, i) in legend" :key="info"><span :class="'mark-' + i"/>{{ info }}</p>
        </div>
      </div>
    </div>
    <br>
    <div class="section">
      <div class="filters">
        <div class="filter" :class="{ 'active': filter.active }" v-for="filter in filters" :key="filter.name" @click="setFilter(filter)">{{filter.name}}</div>
      </div>
    </div>
    <br>
    <div class="section">
      <div class="adresses">
        <div v-for="address in addressesFiltered" :key="address.addr.guid" 
            class="address"
            :class="{ 'expanded': address.expanded }"
            @click="expand(address)">{{address.addr.outaddr}}</div>
      </div>
    </div>
    <br>
    <div class="section">
      <b-button-group>
          <b-button class="btn-theme-blue"
                    size="md"
                    @click="download()">
                    <b-icon-arrow-down-circle class="mr-2"/>{{ downloadLabelComputed }}
          </b-button>
          <b-dropdown right class="btn-theme-blue">
            <b-dropdown-item @click="download('xlsx')">.xlsx</b-dropdown-item>
            <b-dropdown-item @click="download('csv')">.csv</b-dropdown-item>
          </b-dropdown>
        </b-button-group>
    </div>
	</div>
</template>

<script>
import VueChartist from 'v-chartist'
import Chartist from 'chartist'
export default {
  props: {
    total: String,
    done: String,
    errors: String,
    verify: String
  },
  data () {
    return {
      addresses: [],
      filters: [
        { name: 'Распознано', active: false, delivery: 0, filter: ({addr}) => (addr.delivery === 0) },
        { name: 'Не распознано', active: false, delivery: 2, filter: ({addr}) => (addr.delivery === 2) },
        { name: 'Частично распознано', active: true, delivery: 1, filter: ({addr}) => (addr.delivery === 1) },
        { name: 'Все', active: false, filter: () => { return true } }
      ],
      listener: {
        draw (data) {
          if (data.type === 'slice') {
            let pathLength = data.element._node.getTotalLength()

            data.element.attr({
              'stroke-dasharray': pathLength + 'px ' + pathLength + 'px'
            })

            let animationDefinition = {
              'stroke-dashoffset': {
                id: 'anim' + data.index,
                dur: 1000,
                from: -pathLength + 'px',
                to:  '0px',
                easing: Chartist.Svg.Easing.easeOutQuint,
                fill: 'freeze'
              },

            }

            if (data.index !== 0) {
              animationDefinition['stroke-dashoffset'].begin = 'anim' + (data.index - 1) + '.end'
            }

            data.element.attr({
              'stroke-dashoffset': -pathLength + 'px'
            })
            data.element.animate(animationDefinition, false)
          }
        }
      },
      legend: ['Распознано', 'Не распознано', 'Частично распознано'],
      totalCount: this.total,
      data: {
        series: [this.done, this.errors, this.verify]
      },
      options: {
        height: '400px',
        width: '400px',
        donut: true,
        donutWidth: 60,
        showLabel: true
      }
    }
	},
	created () {
    document.addEventListener('dragenter', this.handleDragEnter)
    this.getAll()
	},
  methods: {
    expand (address) {
      address.expanded = !address.expanded
    },
    setFilter (filter) {
      this.filters.forEach(f => (f.active = f.name === filter.name))
    },
    getAll () {
      return this.$bs.getCleanAddresses()
        .then(data => { this.addresses = data.map(item => ({ ...item, expanded: false })) })
    },
    // Скачиваем файл
    download (type = 'xlsx') {
      this.$bs.downloadCleanAddresses(type, this.activeFilter.delivery)
    }
  },
  computed: {
    addressesFiltered () {
      // return this.addresses.filter(this.activeFilter.filter)
      return []
    },
    filename () {
      return this.$root.fileName || 'Результат обработки'
    },
    activeFilter () {
      return this.filters.find(f => f.active)
    },
    downloadLabelComputed () {
      const matrix = {
        'Распознано': 'Выгрузить распознанные адреса',
        'Не распознано': 'Выгрузить нераспознанные адреса',
        'Частично распознано': 'Выгрузить частично распознанные адреса',
        'Все': 'Выгрузить все'
      }
      return matrix[this.activeFilter.name]
    }
  },
	components: {
    'vue-chartist': VueChartist
	}
}
</script>

<style lang="stylus">
@require '../styles/chartist.min.css'

.ct-label
  font-size 15px
  font-weight bold
  &:nth-child(1)
    fill white
  &:nth-child(2)
    fill black
  &:nth-child(3)
    fill black

.ct-series-a
  path
    stroke #0055A6 !important

.ct-series-b
  path
    stroke #90a4ae !important

.ct-series-c
  path
    stroke #cfd8dc !important
</style>

<style  lang="stylus" scoped>
.filters
  display flex
  .filter
    padding 8px 30px
    background-color #F7F8FA
    border-radius 10px
    color black
    font-weight 500
    font-size .8rem
    margin-right 20px
    cursor pointer
    transition background-color .15s ease
    &.active
      background-color #0055A6
      color white
.fullscreen
  display flex
  flex-direction column
  align-items center
  min-height 80vh

.section
  display flex
  flex-direction column
  width 800px
  max-width 100%

  h2
    font-weight normal
    margin 30px 0

  .chart
    font-weight 500
    display flex
    justify-content center
    position relative
    padding 100px 30px 30px 30px
    background #FFFFFF
    box-shadow 0px 0px 30px rgba(0, 0, 0, 0.1)
    border-radius 10px

    .total
      position absolute
      top 51%
      display flex
      flex-wrap wrap
      align-items flex-end
      h1
        font-weight bold
        margin 0 5px 0 0
        line-height 1
        font-size 3rem
    .descr
      position absolute
      top 40px
      left 40px
      width 250px
    .legend
      position absolute
      top 40px
      right 40px
      p
        margin 0
        display flex
        align-items center
        span
          display block
          width 10px
          height 10px
          margin-right 8px
          border-radius 50%
          &.mark
            &-0
              background-color #0055A6
            &-1
              background-color #90a4ae
            &-2
              background-color #cfd8dc
</style>
