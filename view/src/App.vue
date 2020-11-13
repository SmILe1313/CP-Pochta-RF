<template>
  <div id="app">
      <!-- <b-navbar toggleable="lg" type="dark" variant="primary" v-if="loggedIn">

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#/">Главная</b-nav-item>
            <b-nav-item href="#/handle">Обработка</b-nav-item>
          </b-navbar-nav>

          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown right>
              <template #button-content>
                <em>Пользователь</em>
              </template>
              <b-dropdown-item href="#">Профиль</b-dropdown-item>
              <b-dropdown-item href="#">Выход</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar> -->

      <router-view/>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  methods: {
  },
  computed: {
  }
}
</script>
<style lang="stylus">
.text-theme
  &-blue
    color #0055A6
  &-gray
    color #000000
    opacity .4


.btn-theme-blue
  border none
  border-radius 4px
  background linear-gradient(263.82deg, #0064C5 4.04%, #004C9B 95.62%)

.inp-theme
  &-blue
    border-radius 0
    border 1px solid #E6E6E6
    &::placeholder
      color #E6E6E6
  &-whiteblue
    background-color transparent
    border-radius 0
    border 1px solid #E6E6E6
    color #E6E6E6
    &::placeholder
      color #E6E6E6

.custom-file-input:lang(ru)
  ~ .custom-file-label
    &::after
      content 'Просмотр'

</style>
