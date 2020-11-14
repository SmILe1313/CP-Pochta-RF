<template>
	<div class="settings-layout" @click.self="close">
    <div class="settings-box">
      <div class="settings-header">
        <div v-for="setting in settings"
            class="setting-item"
            :class="{ 'used': usedSettings.includes(setting.key) }"
            :key="setting.key" draggable
             @dragstart="writeSetting(setting)">{{setting.name}}</div>
      </div>
      <div class="settings-table">
        <b-row align-v="center" class="s-row">
          <b-col class="s-col m-2 empty" v-for="col in cols" :key="col"
                @dragover.prevent
                @dragleave.prevent
                @dragenter="handleDragEnter"
                @dragleave="handleDragLeave"
                @drop="e => handleDrop(e, col)"
                @click="e => clearSetting(e, col)"
                >
            {{settingRow[col]}}
          </b-col>
        </b-row>

        <b-row align-v="center" class="s-row" v-for="(row, i) in rows" :key="i">
          <b-col class="s-col p-2" v-for="col in cols" :key="col">
            {{row[col]}}
          </b-col>
        </b-row>
      </div>
      <div class="over-button">
        <b-button size="lg"
            class="btn-theme-blue mt-5 py-2 px-5"
            @click="applySetting()">
              Применить
        </b-button>
      </div>
    </div>
    
	</div>
</template>

<script>
import XLSX from 'xlsx'
export default {
  props: {
    file: File
  },
  data () {
    return {
      settings: [
        { name: 'ФИО', key: 'fio', _used: false },
        { name: 'Адрес', key: 'addr', _used: false },
        { name: 'Город', key: 'city', _used: false },
        { name: 'Улица', key: 'street', _used: false },
        { name: 'Дом', key: 'home', _used: false },
        { name: 'Квартира', key: 'room', _used: false },
        { name: 'ID', key: 'id', _used: false }
      ],
      cols: [],
      rows: [],
      settingRow: {},
      transgetSetting: {}
    }
	},
	created () {
    this.readFile()
	},
  methods: {
    handleDrop (e, col) {
      const el = e.target
      el.style.backgroundColor = '#0055A6'
      el.style.color = 'white'
      el.style.border = ''
      el.style.transform = ''
      
      this.$set(this.settingRow, col, this.transgetSetting)
      this.settings.find(s => s.key === this.transgetSetting.key)._used = true
      el.innerText = this.transgetSetting.name
      el.$filled = true
    },
    handleDragLeave (e) {
      const el = e.target
      if (el.$filled) {
        el.style.backgroundColor = '#0055A6'
        el.style.color = 'white'
        el.style.border = ''
        el.style.transform = ''
        return
      }
      el.style.backgroundColor = ''
      el.style.border = ''
      el.style.transform = ''
    },
    handleDragEnter (e) {
      const el = e.target
      el.style.backgroundColor = '#ffffff'
      el.style.border = '1px dashed #676768'
      el.style.transform = 'scale(1, 1.3)'
    },
    writeSetting (setting) {
      this.transgetSetting = { ...setting }
    },
    clearSetting (e, col) {
      const el = e.target
      el.innerText = ''
      el.style.backgroundColor = ''
      el.style.border = ''
      el.style.transform = ''
      el.$filled = false
      this.$set(this.settingRow, col, null)
    },
    close () {
      this.$emit('close')
    },
    applySetting () {
      this.$emit('setFileScheme', { ...this.settingRow }),
      this.close()
    },
    readFile () {
      let reader = new FileReader()

      reader.onload = (e) => {
        let data = e.target.result
        let workbook = XLSX.read(data, { type: 'binary' })

        workbook.SheetNames.forEach((sheetName) => {
          const rows = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName])
          const [row] = rows
          this.cols = Object.keys(row)
          this.rows = rows.slice(0, 4)
          this.settingRow = this.cols.reduce((acc, col) => ({ ...acc, [col]: null }), {})
        })
      }

      reader.onerror = (ex) => {
        console.log(ex)
      }

      reader.readAsBinaryString(this.file)
    }
  },
  computed: {
    usedSettings () {
      return Object.values(this.settingRow).filter(val => val).map(s => s.key) || []
    }
  },
	components: {
	}
}
</script>

<style  lang="stylus" scoped>
.settings-table
  display flex
  flex-direction column
  width 100%
  .s-row
    height 50px
    margin 0 !important
    &:nth-child(odd)
      background-color #0055a626
    .s-col
      white-space nowrap 
      overflow hidden
      text-overflow ellipsis
      transition transform .2s ease
      &.empty
        background #F7F8FA
        border-radius 6px
        height 30px
        cursor pointer

    

.settings-header
  display flex
  justify-content space-evenly
  padding 20px
  .setting-item
    padding 8px 25px
    background #F7F8FA
    border-radius 10px
    cursor move
    &.used
      background #0055A6
      color white

.over-button
  position absolute
  bottom -60px
  left calc(50% - 89px)

.settings-box
  position relative
  display flex
  flex-direction column
  width 60%
  min-width 800px
  height 40%
  margin auto
  background #FFFFFF
  box-shadow 0px 0px 10px rgba(0, 0, 0, 0.05)
  border-radius 10px
  color black
  font-weight 500

.settings-layout
  display flex
  width 100vw
  height 100vh
  position fixed
  z-index 100
  top 0
  left 0
  background #80808030
</style>
