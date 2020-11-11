<template>

    <b-tabs v-model="tabIndex" content-class="mt-3">

      <b-tab title="Загрузить">
        <b-container>

          <b-form-file
            v-show="!loading"
            v-model="file.data"
            :state="Boolean(file.data)"
            placeholder="Выберите или перетащите файл..."
            drop-placeholder="Перетащите сюда..."
            size="lg"
          ></b-form-file>
          <b-progress height="48px" :value="file.uploaded" :max="100" show-progress animated v-show="loading"></b-progress>

          <div class="mt-3">Выбранный файл: {{ file.data ? file.data.name : '' }}</div>
          <b-button @click="upload" :disabled="!file.data" class="mr-2">Загрузить</b-button>

        </b-container>
      </b-tab>
      <b-tab title="Просмотр" lazy>
        <template #title>
          <b-spinner type="border" small v-if="loading"></b-spinner> Просмотр
        </template>

        <b-table
          responsive
          selectable
          no-select-on-click
          sticky-header="calc(100vh - 130px)"
          striped
          bordered
          small
          hover
          head-variant="light"
          @row-selected="onRowSelected"
          :items="items"
          :fields="fields">
            
            <!-- Выделенные строки оборачиваем в инпуты -->
            <template v-slot:cell()="{ item, field, value, rowSelected }">
              <template v-if="rowSelected">
                <b-input size="sm" v-model="item[field.key]" @change="changeStatus(item)"/> 
              </template>
              <template v-else>
                {{ value }}
              </template>
            </template>

            <!-- Чекбоксы -->
            <template #cell(check)="data">
              <div class="tools">
                <b-button @click="toggleRow(data)" variant="primary" size="sm">
                  <b-icon-pencil-square/>
                </b-button>

                <b-button v-if="data.item._changed" @click="save(data.item)" variant="success" size="sm">
                  <b-icon-hdd/>
                </b-button>
              </div>
            </template>

          </b-table>

        </b-table>

      </b-tab>

    </b-tabs>
</template>

<script>
import { fieldsArray as FIELDS } from '@/data/fields'
export default {
  data () {
    return {
      file: {
        data: null,
        uploaded: 0,
        error: false,
        responseReceived: false,
        get showProgress () { return this.uploaded < 100 || !this.responseReceived }
      },
      loading: false,
      tabIndex: 0,
      fields: FIELDS,
      items: [
        { id: 40, 'address-type': 'Dickerson', area: 'Macdonald', _changed: false, _rowVariant: '' },
        { id: 21, 'address-type': 'Larsen', area: 'Shaw', _changed: false, _rowVariant: '' },
        { id: 89, 'address-type': 'Geneva', area: 'Wilson', _changed: false, _rowVariant: '' },
        { id: 38, 'address-type': 'Jami', area: 'Carney', _changed: false, _rowVariant: '' },
        { id: 40, 'address-type': 'Dickerson', area: 'Macdonald', _changed: false, _rowVariant: '' },
        { id: 21, 'address-type': 'Larsen', area: 'Shaw', _changed: false, _rowVariant: '' },
        { id: 89, 'address-type': 'Geneva', area: 'Wilson', _changed: false, _rowVariant: '' },
        { id: 38, 'address-type': 'Jami', area: 'Carney', _changed: false, _rowVariant: '' },
        { id: 40, 'address-type': 'Dickerson', area: 'Macdonald', _changed: false, _rowVariant: '' },
        { id: 21, 'address-type': 'Larsen', area: 'Shaw', _changed: false, _rowVariant: '' },
        { id: 89, 'address-type': 'Geneva', area: 'Wilson', _changed: false, _rowVariant: '' },
        { id: 38, 'address-type': 'Jami', area: 'Carney', _changed: false, _rowVariant: '' },
        { id: 40, 'address-type': 'Dickerson', area: 'Macdonald', _changed: false, _rowVariant: '' },
        { id: 21, 'address-type': 'Larsen', area: 'Shaw', _changed: false, _rowVariant: '' },
        { id: 89, 'address-type': 'Geneva', area: 'Wilson', _changed: false, _rowVariant: '' },
        { id: 38, 'address-type': 'Jami', area: 'Carney', _changed: false, _rowVariant: '' },
        { id: 40, 'address-type': 'Dickerson', area: 'Macdonald', _changed: false, _rowVariant: '' },
        { id: 21, 'address-type': 'Larsen', area: 'Shaw', _changed: false, _rowVariant: '' },
        { id: 89, 'address-type': 'Geneva', area: 'Wilson', _changed: false, _rowVariant: '' },
        { id: 38, 'address-type': 'Jami', area: 'Carney', _changed: false, _rowVariant: '' },
        { id: 40, 'address-type': 'Dickerson', area: 'Macdonald', _changed: false, _rowVariant: '' },
        { id: 21, 'address-type': 'Larsen', area: 'Shaw', _changed: false, _rowVariant: '' },
        { id: 89, 'address-type': 'Geneva', area: 'Wilson', _changed: false, _rowVariant: '' },
        { id: 38, 'address-type': 'Jami', area: 'Carney', _changed: false, _rowVariant: '' }
      ]
    }
  },
  methods: {
    upload () {
      this.loading = true
      console.log(this.fields)
      this.$bs.uploadFileAsync(this.file)
        .then(data => {
          console.log(data)
          setTimeout(() => {
            this.loading = false
            this.tabIndex = 1
          }, 1000)
        })
    },
    onRowSelected (row) {
      console.log('Выделена строка:', row)
    },
    changeStatus (row) {
      row._changed = true
      row._rowVariant = 'warning'
    },
    toggleRow ({ rowSelected, selectRow, unselectRow }) {
      if (rowSelected) {
        unselectRow()
      } else {
        selectRow()
      }
    },
    save (row) {
      this.$bs.updateAsync(row, row.id)
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
