<template>

    <b-tabs v-model="tabIndex">

      <b-tab title="Загрузка">
        <b-container class="mt-5 mb-5">
        <b-card title="Загрузка файла">
          <b-card-text>Загрузите подготовленный файл для обработки в формате .xlsx или .csv.</b-card-text>
          <b-form-file
            v-show="!loading"
            v-model="file.data"
            :state="Boolean(file.data)"
            placeholder="Выберите или перетащите файл..."
            drop-placeholder="Перетащите сюда..."
            class="mt-4 mb-4"
            size="lg"
          ></b-form-file>
          <b-progress height="48px" :value="file.uploaded" :max="100" show-progress animated v-show="loading"></b-progress>

          <b-row align-v="center" align-h="between" class="p-3" >
            <b-button @click="upload" :disabled="!file.data" variant="primary" size="lg">Начать обработку</b-button>
            <b-link href="#/" class="card-link">Как это работает?</b-link>
          </b-row>

        </b-card>
        </b-container>
      </b-tab>

      <b-tab title="Просмотр" lazy :disabled="!addressesAll.length">
        <template #title>
          <b-spinner type="border" small v-if="loading"></b-spinner> Просмотр
        </template>
        <tablePreview :data="addressesAll"/>
      </b-tab>

      <b-tab title="Исправление" content-class="mt-3" :disabled="!addressesWithError.length">
        <editCard :data="addressesWithError"/>
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
      addressesAll: [],
      addressesWithError: [],
      loading: false,
      tabIndex: 0
    }
  },
  methods: {
    
    upload () {
      this.loading = true
      this.$bs.uploadFileAsync(this.file)
        .then(() => this.getWithErrors())
        .then(() => this.getAll())
        .then(() => {
          setTimeout(() => {
            this.loading = false
            this.tabIndex = 1
          }, 1000)
        })
    },

    getWithErrors () {
      return this.$bs.getCleanAddressesWithErrors()
        .then(data => { this.addressesWithError = data})
    },

    getAll () {
      return this.$bs.getCleanAddresses()
        .then(data => { this.addressesAll = data })
    }
  },
  components: {
    tablePreview,
    editCard
  }
}
</script>
