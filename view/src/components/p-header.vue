<template>
  <header class="mt-3">
    <div class="header-info">
        <logo class="login-logo"/>
        <b-link href="#/about" class="text-theme-blue"><b-icon-question-circle size="sm"/> Помощь </b-link>
    </div>
    <hr/>
  </header>
</template>

<script>
import logo from '@/icons/logo'
export default {
	components: {
		logo
	}
}
</script>

<style lang="stylus" scoped>
header
  display flex
  flex-direction column
  align-items center
  flex 1
  margin-left -15px
  margin-right -15px
  .header-info
    display flex
    justify-content space-between
    align-items center
    width 60%

  hr
    width 100%

</style>