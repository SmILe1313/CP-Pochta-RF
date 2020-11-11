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

const backLink = 'http://192.169.6.6:9000'
const apiExcel = '/excel'

const bs = {

  // Получить entityName (тут просто название) по id
  getDataAsync (entityName, id) {
    return HTTP.get(backLink + '/' + entityName + '/' + id)
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
  },

  // Создать объект entity (тут объект целиком)
  createAsync (entity) {
    const cnfBody = {
      data: entity
    }
    return HTTP.post(backLink + '/' + entity + cnfBody)
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
  },

  // Обновить entity (тут объект целиком) по id
  updateAsync (entity, id) {
    const cnfBody = {
      data: entity
    }
    return HTTP.put(backLink + '/update' + '/' + id, cnfBody)
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
  },

  // Удалить entityName (тут просто название) по id
  deleteAsync (entityName, id) {
    // let cnfBody = {
    //   data: entity
    // }
    return HTTP.delete(backLink + '/' + entityName + '/' + id)
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
    return HTTP.post(backLink + apiExcel + '/clean/address', formData, config)
      .then(resp => {
        file.error = resp.error // условно
        file.responseReceived = true
        return resp
      })
      .catch(e => {
        console.log(e)
        file.uploaded = 100
        file.error = true
        file.responseReceived = true
      })

    // const fantom = setInterval(() => {
    //   if (file.uploaded < 100)  {
    //     file.uploaded += 10
    //   } else {
    //     file.responseReceived = true
    //     clearInterval(fantom)
    //   }
    // }, 100)

    // const delay = (ms) => {
    //   return new Promise(resolve => setTimeout(resolve, ms))
    // }
    // return delay(1000).then(() => file.data)
  },

  // Скачать нормализованный эксель-файл в виде xlsx/csv
  downloadCleanAddresses (type) {
    return this.getExeclDataAsync(backLink + apiExcel + "/download/" + type)
  },

  // // Скачать нормализованный эксель-файл в виде csv
  // downloadCleanAddressesCSV() {
  //   return getExeclDataAsync(backLink + apiExcel + "/download/csv")
  // },

  // Получить из базы список нормализованных адресов
  getCleanAddresses () {
    return getExeclDataAsync(backLink + apiExcel + "/get/clean")
  },

  // Получить из базы список НЕнормализованных адресов
  getToCleanAddresses () {
    return getExeclDataAsync(backLink + apiExcel + "/get/toclean")
  },

  // Общий метод для экселя
  getExeclDataAsync (apiLink) {
    return HTTP.get(apiLink)
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
