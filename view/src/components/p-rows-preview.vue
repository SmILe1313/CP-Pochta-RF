<template>
  <div class="adresses">
    <div v-for="address in addresses" :key="address.addr.guid" 
        class="address">

        <!-- Шапка -->
        <div class="label"
            :class="{ 'error': address.addr.delivery === 2,
                      'verify': address.addr.delivery === 1 }"
            @click="expand(address)">
          <p :title="address.addr.outaddr">{{address.addr.outaddr}}</p>
          <b-icon-caret-up-fill class="expander" key="up" v-if="address.expanded"/>
          <b-icon-caret-down-fill class="expander" key="down" v-else/>
        </div>

        <!-- Форма -->
        <collapse>
        <template v-if="address.expanded">
            <b-form class="pb-4">
              <!-- Поля общие -->
              <b-form-row>
                <b-col key="fio" class="my-2">
                  <label for="fio">ФИО</label>
                  <b-form-input
                    class="inp-theme-blue"
                    id="fio"
                    type="text"
                    v-model="address._info.fio"
                    @input="changeStatus(address)"/>
                </b-col>
                <b-col key="index" class="my-2">
                  <label for="index">Индекс</label>
                  <b-form-input
                    class="inp-theme-blue"
                    id="index"
                    type="text"
                    v-model="address._info.index"
                    @input="changeStatus(address)"/>
                </b-col>
              </b-form-row>

              <b-form-row>
                <b-col v-for="element in address._elements" :key="element.content" class="my-2" cols="4">
                <label :for="element.content">{{ element.content === 'C' ? 'Страна' : element.tname }}</label>
                <b-form-input
                    class="inp-theme-blue"
                    :id="element.content"
                    type="text"
                    :placeholder="element.tname ? 'Введите ' + element.tname.toLowerCase() : ''"
                    v-model="element.val"
                    @input="changeStatus(address)"/>

                </b-col>
              </b-form-row>

              <b-row>
                  <b-button size="md"
                            class="btn-theme-blue my-3 mx-3"
                            @click="save(address)">
                            Сохранить
                  </b-button>
              </b-row>

            </b-form>
        </template>
        </collapse>
    </div>
  </div>
</template>

<script>
const SYSTEM_FIELDS = {
  _saving: false,
  _saved: false,
  _changed: false,
  _error: false
}
import { fieldsByGroup, fieldsToEdit } from '@/data/fields'
import collapse from './accordion'

export default {
  props: {
    data: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      groups: fieldsByGroup,
      addresses: this.data.map(address => ({ ...address, ...SYSTEM_FIELDS }))
    }
  },
  watch: {
    data (to) {
      this.addresses = to.map(address => ({ ...address, ...SYSTEM_FIELDS }))
    }
  },
  methods: {
    expand (address) {
      address.expanded = !address.expanded
    },
    changeStatus (address) {
      address._changed = true
    },
    // Сохряем одиночную запись
    save (address) {
      const addressOrderKeys = ['C', 'R', 'A', 'P', 'T', 'S', 'N', 'L', 'D', 'E', 'B', 'F']
      const index = address._info.index ? address._info.index + ', ' : ''
      const addressFilled = address._elements.filter(field => field.val)
      const addressString = addressOrderKeys.reduce((acc, key) => {
        let field = addressFilled.find(field => field.content === key)
        if (field && field.val) {
          return [...acc, field.val]
        }
        return acc
      }, [])
      const data = {
        id: address.id,
        addr: index ? index + [addressString].join(',') : [addressString].join(',')
      }
      address._saving = true
      const clearSaving = () => setTimeout(() => {
        address._saving = false
        address._changed = false
      }, 1500)
      return this.$bs.updateAsync(data, address.id)
        .then(resp => {
            address._saved = true
            console.log('saved')
            clearSaving()
          })
          .catch(e => {
            address._error = true
            console.log(e)
            clearSaving()
          })
    },

  },
  computed: {
  },
  components: {
    collapse
  }
}
</script>

<style lang="stylus" scoped>

.address
  display flex
  flex-direction column
  margin-bottom 15px
  flex-shrink 0
  cursor pointer
  position relative
  
  .label
    border 1px solid #E6E6E6
    display flex
    align-items center
    &.error
      border 1px solid #E11C1C
    &.verify
      border 1px solid #ff9800
    p
      max-width 95%
      overflow hidden
      text-overflow ellipsis
      white-space nowrap
      margin 5px
      font-weight 500
    .expander
      display block
      position absolute
      right 10px
      width 10px
      height 10px
</style>