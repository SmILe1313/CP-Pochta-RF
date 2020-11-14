import axios from 'axios'

const HTTP = axios.create({
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  },
  xsrfHeaderName: 'Csrf-Token',
  // withCredentials: true,
  maxContentLength: 50000000,
  timeout: 150000
})

const backLink = 'http://192.169.6.10:9000'
const apiExcel = '/excel'
const apiController = '/api'

const bs = {

  // Получить entityName (тут просто название) по id
  getDataAsync (entityName) {
    return HTTP.get(backLink + '/' + entityName)
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Создать объект entity (тут объект целиком)
  createAsync (entity) {
    return HTTP.post(backLink + '/', entity)
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Обновить entity (тут объект целиком) по id
  updateAsync (entity, id) {
    return HTTP.put(backLink + '/clean' + '/' + id, entity)
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Удалить entityName (тут просто название) по id
  deleteAsync (entityName, id) {
    return HTTP.delete(backLink + '/' + entityName + '/' + id)
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Отправить НЕнормализованный файл для обработки
  uploadFileAsync (file) {
    const config = {
      headers: { 'Content-Type': 'multipart/form-data' },
      onUploadProgress: (progressEvent) => {
        const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total)
        file.uploaded = percentCompleted
      }
    }
    let formData = new FormData()
    formData.append('files', file.data)
    formData.append('filelink', file.link)
    return HTTP.post(backLink + apiExcel + '/clean/address', formData, config)
      .then(({ data }) => {
        file.error = !data // условно
        file.responseReceived = true
        return data
      })
      .catch(e => {
        console.log(e)
        file.uploaded = 100
        file.error = true
        file.responseReceived = true
      })
  },

  // Скачать нормализованный эксель-файл в виде xlsx/csv
  downloadCleanAddresses (type, delivery) {
    const query = delivery ? '?delivery=' + delivery : ''
    return this.getExeclDataAsync(backLink + apiExcel + '/download/' + type + query)
      .then(resp => {
        const blob = new Blob([resp], { type:'application/vnd.ms-excel '})
        let link = document.createElement('a')
        let date = new Date()
        link.download = "Нормализованные адреса_" + date.getDate() + (date.getMonth() + 1) + date.getFullYear() + "." + type
        link.href = window.URL.createObjectURL(blob)
        link.click()
        link.remove()
      })
  },

  // // Скачать нормализованный эксель-файл в виде csv
  // downloadCleanAddressesCSV() {
  //   return getExeclDataAsync(backLink + apiExcel + "/download/csv")
  // },

  // Получить из базы список нормализованных адресов С ОШИБКАМИ
  getCleanAddressesWithErrors () {
    return this.getDataAsync("clean/witherrors")
  },

  // Получить из базы список нормализованных адресов
  getCleanAddresses () {
    return this.getDataAsync("clean/all")
  },

  // Получить из базы список оригинальных адресов
  getToCleanAddresses () {
    return this.getDataAsync("toclean/all")
  },

  // Общий метод для экселя
  getExeclDataAsync (apiLink) {
    return HTTP.get(apiLink, {
      responseType: 'blob'
      })
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Получить нормализованный адрес по пулу строк
  getNormalizeByString (entity) {
    return HTTP.post(backLink + apiController + '/normalizeByString', entity)
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Логинимся. В ответ придет user
  doLogin(log, pass) {
    let body = {
      login: log, password: pass
    }
    return HTTP.post(backLink + apiController + '/login', body)
      .then(({ data }) => {
        if (data) {
          return Promise.resolve(data)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
  },

  // Просто тестовый метод, который отправляет запрос на бэк, а тот отправляет на сторонний сервис
  getTest () {
    return HTTP.get(backLink + '/testGetService')
      .then(resp => {
        if (resp !== 'error') {
          return Promise.resolve(resp)
        } else {
          return Promise.reject(new Error('getting error'))
        }
      })
      .catch(e => {
        console.log(e)
      })
    }
}
export default bs
