<template>
  <b-container class="mb-5">
    <b-overlay v-if="itemToEdit" :show="itemToEdit._saving">
    
    <b-form class="mt-4 mb-4">
      <b-form-input
        id="search"
        type="text"
        placeholder="Город, улица, дом, квартира одной строкой"
        v-model="search"
        debounce="500"
        autofocus
        list="results"
        @input="getAddress()"/>

        <datalist id="results">
          <option v-for="result in results" :key="result">{{ result }}</option>
        </datalist>
    </b-form>

    <b-card :header-bg-variant="itemToEdit._rowVariant"
            :header="titleComputed"
            :header-text-variant="itemToEdit._rowVariant ? 'white': ''">
      <b-form>
        <b-form-row v-for="(group, i) in groups" :key="i">
          <b-col v-for="field in group" :key="field.key" class="m-2">
          
          <label :for="field.key">{{ field.label }}</label>
          <b-form-input
            :id="field.key"
            :type="field.type"
            v-model="itemToEdit[field.key]"
            @input="changeStatus(itemToEdit)"/>

          </b-col>
        </b-form-row>

        <b-row align-h="end" class="pr-3">
            <b-button variant="success"
                      size="md"
                      class="mr-2"
                      :disabled="!itemToEdit._changed"
                      @click="save()">
                      <b-icon-hdd class="mr-2"/>Сохранить
            </b-button>
            <b-button variant="primary "
                      size="md"
                      class="mr-2"
                      @click="skip()">
                      <b-icon-hdd class="mr-2"/>Пропустить
            </b-button>
        </b-row>

      </b-form>
    </b-card>
    </b-overlay>
    <b-card v-else title="Нет записей для исправления">
      <b-card-text> Все записи имеют корректный адрес.</b-card-text>

      <b-card-text>Вы можете выгрузить обработанные адреса</b-card-text>

      <b-button-group>
          <b-button variant="primary"
                    size="md"
                    @click="download()">
                    <b-icon-arrow-down-circle class="mr-2"/>Выгрузить
          </b-button>
          <b-dropdown right variant="primary">
            <b-dropdown-item @click="download('xlsx')">.xlsx</b-dropdown-item>
            <b-dropdown-item @click="download('csv')">.csv</b-dropdown-item>
          </b-dropdown>
        </b-button-group>
    </b-card>
  </b-container>
</template>

<script>
const SYSTEM_FIELDS = {
  _saving: false,
  _saved: false,
  _changed: false,
  _error: false,
  _rowVariant: ''
}
import { fieldsByGroup as FIELDS_BY_GROUP } from '@/data/fields'
export default {
  props: {
    data: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      groups: FIELDS_BY_GROUP,
      items: this.data.map(row => ({ ...row, ...SYSTEM_FIELDS })),
      editItemId: 0,
      search: '',
      results: ['Москва', 'Мурманск', 'Магнитогорск', 'Марьино', 'Мирный']
    }
  },
  watch: {
    data (to, fr) {
      this.items = this.data.map(row => ({ ...row, ...SYSTEM_FIELDS }))
    }
  },
  methods: {
    // Скачиваем файл
    download (type = 'xlsx') {
      this.$bs.downloadCleanAddresses(type)
    },
    // Пропускаем карточку
    skip () {
      if (this.editItemId + 1 < this.totalComputed) {
        this.editItemId += 1
      } else {
        this.editItemId = 0
      }
    },
    // Изменяем статус строки
    changeStatus (row) {
      row._changed = true
      row._rowVariant = 'info'
    },
    // Сохряем одиночную запись
    save () {
      const row = this.itemToEdit
      row._saving = true
      const clearSaving = () => setTimeout(() => {
        row._saving = false
        row._changed = false
        row._rowVariant = ''
        this.items = this.items.filter(item => (item.id !== row.id))
      }, 1500)
      return this.$bs.updateAsync(row, row.id)
        .then(resp => {
            row._saved = true
            console.log('saved')
            clearSaving()
          })
          .catch(e => {
            row._error = true
            console.log(e)
            clearSaving()
          })
    },

    // Получаем нормализованный адрес от api почты рф
    getAddress () {
      console.log('Запросить дату')
    }
  },
  computed: {
    itemToEdit () {
      const key = this.editItemId
      return this.items[key]
    },
    totalComputed () {
      return this.items.length
    },
    titleComputed () {
      return `Запись ${this.editItemId + 1} из ${this.totalComputed}`
    }
  }
}
</script>

<style lang="stylus" scoped>
</style>