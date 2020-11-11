<template>

    <b-tabs v-model="tabIndex">

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

        <tablePreview/>

      </b-tab>

    </b-tabs>
</template>

<script>
import tablePreview from '@/components/tablePreview'
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
      tabIndex: 0
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
    }
  },
  components: {
    tablePreview
  }
}
</script>

