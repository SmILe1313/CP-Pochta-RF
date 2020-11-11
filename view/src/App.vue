<template>
  <div id="app">
      <b-navbar toggleable="lg" type="dark" variant="primary" v-if="loggedIn">

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
      </b-navbar>

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
    loggedIn () {
      return !this.$route.path.includes('login')
    }
  }
}
</script>
<style lang="stylus">
.custom-file-input:lang(ru)
  ~ .custom-file-label
    &::after
      content 'Просмотр'

</style>
