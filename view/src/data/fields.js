const fieldsObject = {
  check: {
    label: 'Действия',
    type: 'system'
  },
  id: {
    label: 'ID',
    type: 'number',
    disabled: true
  },
  'address-type': {
    label: 'Тип адреса',
    type: 'text'
  },
  area: {
    label: 'Район',
    type: 'text'
  },
  region: {
    label: 'Область, регион',
    type: 'text'
  },
  place: {
    label: 'Населенный пункт',
    type: 'text'
  },
  location: {
    label: 'Микрорайон',
    type: 'text'
  },
  street: {
    label: 'Улица',
    type: 'text'
  },
  house: {
    label: 'Номер здания',
    type: 'text'
  },
  building: {
    label: 'Строение',
    type: 'text'
  },
  corpus: {
    label: 'Корпус',
    type: 'text'
  },
  slash: {
    label: 'Дробь',
    type: 'text'
  },
  letter: {
    label: 'Литера',
    type: 'text'
  },
  room: {
    label: 'Номер помещения',
    type: 'text'
  },
  index: {
    label: 'Почтовый индекс',
    type: 'number'
  },
  hotel: {
    label: 'Название гостиницы',
    type: 'text'
  },
  'num-address-type': {
    label: 'Номер для а/я, войсковая часть, войсковая часть ЮЯ, полевая почта',
    type: 'number'
  },
  'quality-code': {
    label: 'Код качества нормализации адреса',
    type: 'text'
  },
  'validation-code': {
    label: 'Код проверки нормализации адреса',
    type: 'text'
  },
  'original-address': {
    label: 'Оригинальные адрес одной строкой',
    type: 'text'
  }
}
const fieldsGroup = [
  ['address-type','area'],
  ['region', 'place'],
  ['location', 'street', 'house', 'building'],
  ['corpus', 'slash', 'letter', 'room'],
  ['index', 'hotel'],
  ['num-address-type']
]
const fieldsArray = Object.keys(fieldsObject).map(key => ({ 'key': key, ...fieldsObject[key] }))

const nonEditable = ['id', 'check', 'quality-code', 'validation-code', 'original-address']
const fieldsToEdit = fieldsArray.filter(field => !nonEditable.includes(field.key))

const fieldsByGroup = fieldsGroup.reduce((acc, fieldGroup, index) => {
  return {
    ...acc,
    [index]: [
      ...fieldsToEdit.filter(({ key }) => fieldGroup.includes(key))
    ]
  }
}, {})

export { fieldsArray, fieldsObject, fieldsToEdit, fieldsByGroup }
export default fieldsArray
