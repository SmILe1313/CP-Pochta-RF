<template>
  <b-container fluid>
    <b-row class="h-100">
      <b-table
        responsive
        selectable
        no-select-on-click
        sticky-header="calc(100vh - 170px)"
        striped
        bordered
        outlined
        small
        hover
        head-variant="light"
        @row-selected="onRowSelected"
        :items="items"
        :fields="fields">
          
          <!-- Выделенные строки оборачиваем в инпуты -->
          <template v-slot:cell()="{ item, field, value, rowSelected }">
            <template v-if="rowSelected">
              <b-input size="sm" v-model="item[field.key]" @input="changeStatus(item)"/> 
            </template>
            <template v-else>{{ value }}</template>
          </template>

          <!-- Чекбоксы -->
          <template #cell(check)="data">
            <div class="tools">
              <b-button @click="toggleRow(data)" variant="primary" size="sm"><b-icon-pencil-square/></b-button>
              <b-button v-if="data.item._changed" @click="save(data.item)" variant="success" size="sm"><b-icon-hdd/></b-button>
            </div>
          </template>

      </b-table>
    </b-row>

    <b-row align-h="between" align-v="center" class="h-100">
      <b-col cols="6">
        <span> Записей всего: {{ total }} </span>
        <br>
        <span> Ошибок: {{ totalErrors }} </span>
      </b-col>
      <b-col cols="auto">

        <b-button-group>
          <b-button v-if="showSaveAll"
                    variant="success"
                    size="md"
                    @click="saveAll()">
                    <b-icon-hdd class="mr-2"/>Сохранить
          </b-button>
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

      </b-col>
    </b-row>
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
import { fieldsArray as FIELDS } from '@/data/fields'
export default {
  props: {
    data: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      fields: FIELDS,
      items: this.data.map(row => ({ ...row, ...SYSTEM_FIELDS })),
      total: 30,
      totalErrors: this.data.length
    }
  },
  methods: {
    onRowSelected (row) {
      console.log('Выделена строка:', row)
    },

    // Изменяем статус строки
    changeStatus (row) {
      row._changed = true
      row._rowVariant = 'warning'
    },

    // Переключаем выделение строки
    toggleRow ({ rowSelected, selectRow, unselectRow }) {
      if (rowSelected) {
        unselectRow()
      } else {
        selectRow()
      }
    },

    // Сохряем одиночную запись
    save (row) {
      row._saving = true
      const clearSaving = () => setTimeout(() => row._saving = false, 500)
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
    
    // Сохраняем все измененные строки
    saveAll () {
      const savePromise = this.changedRows.map(row => this.save(row))
      Promise.all(savePromise).then(responses => {
        console.log('Получить новые данные')
      })
    },

    // Скачиваем файл
    download (type = 'xlsx') {
      this.$bs.downloadCleanAddresses(type)
    }
  },
  computed: {
    changedRows () {
      return this.items.filter(row => row._changed)
    },
    changedRowsCount () {
      return this.changedRows.length
    },
    showSaveAll () {
      return this.changedRowsCount > 0
    }
  }
}
</script>

<style lang="stylus" scoped>
.tools
  display flex
  flex-shrink 0
  button
    margin 2px
</style>

<style lang="stylus">
.b-table
  tbody
    tr
      &.b-table-row-selected 
        &.table-active
          background-color #4caf504a
          td
            border-color #4caf504a
      td
        padding 1px
        vertical-align middle
        input
          padding 2px


</style>