<template>
	<div class="fullscreen">
    <div class="main-layout">
      <h2> Название файла.xlsx </h2>
      <div class="chart">
        <span class="descr">Статистика по проверенным сведениям и результаты проверок</span>
        <span class="total"><h1>{{total}}</h1> адресов</span>
        <vue-chartist :data="data" :options="options" type="Pie" :listener="listener"></vue-chartist>
        <div class="legend">
          <p v-for="(info, i) in legend" :key="info"><span :class="'mark-' + i"/>{{ info }}</p>
        </div>
      </div>
    </div>
	</div>
</template>

<script>
import VueChartist from 'v-chartist'
import Chartist from 'chartist'
export default {
  data () {
    return {
      addresses: [],
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
      total: 325,
      data: {
        series: [300, 20, 5]
      },
      options: {
        height: '400px',
        width: '400px',
        donut: true,
        donutWidth: 60,
        showLabel: false
      }
    }
	},
	created () {
		document.addEventListener('dragenter', this.handleDragEnter)
	},
  methods: {
    getWithErrors () {
      return this.$bs.getCleanAddressesWithErrors()
        .then(data => { this.addressesWithError = data || [] })
    },
	},
	components: {
    'vue-chartist': VueChartist
	}
}
</script>

<style lang="stylus">
@require '../styles/chartist.min.css'
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
.fullscreen
  display flex
  justify-content center
  min-height 80vh

.main-layout
  display flex
  flex-direction column
  width 800px
  max-width 100%

  h2
    font-weight normal
    margin 30px 0

  .chart
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
