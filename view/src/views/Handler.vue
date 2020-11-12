<template>

    <b-tabs v-model="tabIndex">

      <b-tab title="Загрузка">
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

          <b-button @click="withErrors" class="mr-2">С ошибками</b-button>

          <b-button @click="getAll" class="mr-2">Все</b-button>

          <b-button @click="getByString" class="mr-2">Получить по еб.. т.е. по строке</b-button>

        </b-container>
      </b-tab>

      <b-tab title="Просмотр" lazy :disabled="!items.length">
        <template #title>
          <b-spinner type="border" small v-if="loading"></b-spinner> Просмотр
        </template>
        <tablePreview :data="items"/>
      </b-tab>

      <b-tab title="Исправление" content-class="mt-3" :disabled="!items.length">
        <editCard :data="items"/>
      </b-tab>

    </b-tabs>
</template>

<script>
import tablePreview from '@/components/tablePreview'
import editCard from '@/components/editCard'
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
      items: [],
      loading: false,
      tabIndex: 0
    }
  },
  methods: {
    upload () {
      this.loading = true
      this.$bs.uploadFileAsync(this.file)
        .then(data => {
          this.items = data
          setTimeout(() => {
            this.loading = false
            this.tabIndex = 1
          }, 1000)
        })
    },

    withErrors () {
      this.loading = true
      this.$bs.getCleanAddressesWithErrors()
        .then(data => {
          this.items = data
          setTimeout(() => {
            this.loading = false
            this.tabIndex = 1
          }, 1000)
        })
    },

    getAll () {
      this.loading = true
      this.$bs.getCleanAddresses()
        .then(data => {
          this.items = data
          setTimeout(() => {
            this.loading = false
            this.tabIndex = 1
          }, 1000)
        })
    },

    getByString () {
      let strings = ["maskvaaaaa", "piteeeer", "ebuuurg"]
      this.$bs.getNormalizeByString(strings)
        .then(data => {
          console.log(data)
        })
    }
  },
  components: {
    tablePreview,
    editCard
  }
}
</script>
