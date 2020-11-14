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
                <b-col v-for="uField in userFields" :key="uField" class="my-2">
                  <label :for="uField">ФИО</label>
                  <b-form-input
                    class="inp-theme-blue"
                    :id="uField"
                    type="text"
                    v-model="(address.fio || {})[uField]"
                    @input="changeStatus(address)"/>
                </b-col>
              </b-form-row>

              <b-form-row>
                <b-col v-for="(elField, key) in elementFields" :key="elField.name" class="my-2" cols="3">
                <label :for="elField.name">{{ elField.label }}</label>
                <b-form-input
                    class="inp-theme-blue"
                    :id="elField.name"
                    type="text"
                    :placeholder="'Введите ' + elField.label.toLowerCase()"
                    v-model="(address.addr.element[getelId(address.addr.element, key)] || {}).val"
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

const userFields = ['fullname']
const elementFields = {
  C: { name: 'County', 'label': 'Страна' },
  R: { name: 'Region', 'label': 'Регион' },
  A: { name: 'Area', 'label': 'Район' },
  P: { name: 'Place', 'label': 'Населенный пункт' },
  T: { name: 'Territory', 'label': 'Область' },
  S: { name: 'Street', 'label': 'Улица' },
  N: { name: 'Number', 'label': 'Дом' },
  L: { name: 'Letter', 'label': 'Литера' },
  D: { name: 'Delimiter', 'label': 'Дробь' },
  E: { name: 'External', 'label': 'Корпус' },
  B: { name: 'Building', 'label': 'Строение' },
  F: { name: 'Flat', 'label': 'Помещение' }
}
export default {
  props: {
    data: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      userFields: userFields,
      elementFields: elementFields,
      groups: fieldsByGroup,
      addresses: this.data.map(address => ({ ...address, ...SYSTEM_FIELDS }))
    }
  },
  watch: {
    data (to) {
      this.addresses = this.data.map(address => ({ ...address, ...SYSTEM_FIELDS }))
    }
  },
  methods: {
    getelId(elements, key) {
      const index = elements.findIndex(el => el.content === key)
      return index
    },
    expand (address) {
      address.expanded = !address.expanded
    },
    changeStatus (address) {
      address._changed = true
    },
    // Сохряем одиночную запись
    save (address) {
      address._saving = true
      const clearSaving = () => setTimeout(() => {
        address._saving = false
        address._changed = false
      }, 1500)
      return this.$bs.updateAsync(address, address.id)
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