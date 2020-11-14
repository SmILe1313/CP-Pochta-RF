<template>
  <div class="adresses">
    <div v-for="address in addresses" :key="address.addr.guid" 
        class="address">

        <!-- Шапка -->
        <div class="label"
            :class="{ 'error': address.addr.delivery === 2,
                      'verify': address.addr.delivery === 1 }"
            @click="expand(address)">
          <p>{{address.addr.outaddr}}</p>
          <b-icon-caret-up-fill class="expander" key="up" v-if="address.expanded"/>
          <b-icon-caret-down-fill class="expander" key="down" v-else/>
        </div>

        <!-- Форма -->
        <collapse>
        <template v-if="address.expanded">
            <b-form class="pb-4">
              <b-form-row v-for="(group, i) in groups" :key="i">
                <b-col v-for="field in group" :key="field.key" class="my-2">
                
                <label :for="field.key">{{ field.label }}</label>
                <b-form-input
                  class="inp-theme-blue"
                  :id="field.key"
                  :type="field.type"
                  v-model="address[field.key]"
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
const addrFake = [
  {
    expanded: false,
    addr: {
      quid: 'sdjdsa8-saasdjc0',
      outaddr: '141420, Российская Федерация, обл Московская, г Химки, мкр Сходня, ул Черняховского, д. 34',
      delivery: 1
    }
  },
  {
    expanded: false,
    addr: {
      quid: 'sdjdsa8-saasdjffc0',
      outaddr: '140207, Российская Федерация, обл Московская, г Воскресенск, д Глиньково, д. 13, литера А',
      delivery: 0
    }
  },
  {
    expanded: false,
    addr: {
      quid: 'sdjdsa8-saasdjffc0',
      outaddr: '140209, Российская Федерация, обл Московская, г Воскресенск, с Федино, д. 9, кв 41',
      delivery: 2
    }
  }
]
export default {
  props: {
    data: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      fields: fieldsToEdit,
      groups: fieldsByGroup,
      // addresses: this.data.map(address => ({ ...address, ...SYSTEM_FIELDS }))
      addresses: addrFake.map(address => ({ ...address, ...SYSTEM_FIELDS }))
    }
  },
  watch: {
    data (to) {
      this.addresses = this.data.map(row => ({ ...address, ...SYSTEM_FIELDS }))
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
      address._saving = true
      const clearSaving = () => setTimeout(() => {
        address._saving = false
        address._changed = false
      }, 1500)
      return this.$bs.updateAsync(row, row.id)
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
      margin 5px
      font-weight 500
    .expander
      display block
      position absolute
      right 10px
      width 10px
      height 10px
</style>