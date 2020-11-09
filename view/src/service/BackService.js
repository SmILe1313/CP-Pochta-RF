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

const backLink = 'http://localhost:9000'
const apiExcel = '/excel'

const bs = {

  getDataAsync (entity, id) {
    return HTTP.get(backLink + '/' + entity + '/' + id)
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

  updateAsync (entity, id) {
    const cnfBody = {
      data: entity
    }
    return HTTP.put(backLink + '/' + entity + '/' + id + cnfBody)
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

  deleteAsync (entity, id) {
    // let cnfBody = {
    //   data: entity
    // }
    return HTTP.delete(backLink + '/' + entity + '/' + id)
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

  cleanAddressAsync (file) {
    const cnfBody = {
      data: file
    }
    return HTTP.post(backLink + '/' + apiExcel + '/clean/address' + entity + cnfBody)
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

  getCleanAddresses () {
    return getExeclDataAsync(backLink + apiExcel + "/get/clean")
  },

  getToCleanAddresses () {
    return getExeclDataAsync(backLink + apiExcel + "/get/toclean")
  },

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
