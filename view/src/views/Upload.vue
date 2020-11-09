<template>
  <b-container>

    <b-tabs v-model="tabIndex" content-class="mt-3" fill>

      <b-tab title="Загрузить">

        <b-form-file
          v-model="file"
          :state="Boolean(file)"
          placeholder="Выберите или перетащите файл..."
          drop-placeholder="Перетащите сюда..."
          size="lg"
        ></b-form-file>
        <div class="mt-3">Выбранный файл: {{ file ? file.name : '' }}</div>
        <b-button @click="upload" :disabled="!file" class="mr-2">Загрузить</b-button>

        <b-button @click="testApi"  class="mr-2">Тест</b-button>

      </b-tab>
      <b-tab title="Просмотр" lazy>
        <template #title>
          <b-spinner type="border" small v-if="loading"></b-spinner> Просмотр
        </template>

        <b-table striped hover :items="items"></b-table>

      </b-tab>

    </b-tabs>

  </b-container>
</template>

<script>
export default {
  data () {
    return {
      file: null,
      loading: false,
      tabIndex: 0,
      items: [
        { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
        { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
        { age: 89, first_name: 'Geneva', last_name: 'Wilson' },
        { age: 38, first_name: 'Jami', last_name: 'Carney' }
      ]
    }
  },
  methods: {
    upload () {
      console.log('Загрузить файл')
      this.loading = true
      setTimeout(() => {
        this.loading = false
        this.tabIndex = 1
      }, 4000)
    },
    testApi () {
      this.$bs.getTest().then(eData => {
        console.log(eData)
      })
    }
  }
}
</script>
