const fieldsObject = {
  check: {
    label: 'Действия'
  },
  id: {
    label: 'ID'
  },
  'address-type': {
    label: 'Тип адреса'
  },
  area: {
    label: 'Район'
  },
  region: {
    label: 'Область, регион'
  },
  place: {
    label: 'Населенный пункт'
  },
  location: {
    label: 'Микрорайон'
  },
  street: {
    label: 'Улица'
  },
  house: {
    label: 'Номер здания'
  },
  building: {
    label: 'Строение'
  },
  corpus: {
    label: 'Корпус'
  },
  slash: {
    label: 'Дробь'
  },
  letter: {
    label: 'Литера'
  },
  room: {
    label: 'Номер помещения'
  },
  index: {
    label: 'Почтовый индекс'
  },
  hotel: {
    label: 'Название гостиницы'
  },
  'num-address-type': {
    label: 'Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта'
  },
  'quality-code': {
    label: 'Код качества нормализации адреса'
  },
  'validation-code': {
    label: 'Код проверки нормализации адреса'
  },
  'original-address': {
    label: 'Оригинальные адрес одной строкой'
  }
}

const fieldsArray = Object.keys(fieldsObject).map(key => ({ 'key': key, ...fieldsObject[key] }))

export { fieldsArray, fieldsObject}
export default fieldsArray
