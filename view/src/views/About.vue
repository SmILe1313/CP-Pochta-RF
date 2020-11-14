<template>
  <div class="main-layout">
    <pHeader />
    <div class="section main">
      <div class="section-description">
        <h1>Зачем нужен сервис?</h1>
        <p>Сервис дает возможность произвести нормализацию адресов в базах данных</p>
        <b-row>
          <b-button class="btn-theme-blue ml-3 px-5 py-2" @click="$router.push('/')">Попробовать</b-button>
        </b-row>
      </div>
      <mac/>
    </div>
    <div class="section gray">
      <div class="slider">
        <h1>Как это работает?</h1>
        <pSlider/>
      </div>
    </div>
    <pFooter/>
  </div>
</template>
<script>
import pHeader from '@/components/p-header'
import pFooter from '@/components/p-footer'
import mac from '@/icons/mac.vue'
import pSlider from '@/components/p-slider'
export default {
  components: {
    pHeader,
    pFooter,
    pSlider,
    mac
	}
}
</script>
<style lang="stylus" scoped>
header
  margin 0 !important

.main-layout
  min-height 100vh
  display flex
  flex-direction column
  align-content center

.section
  display flex
  width 100%
  height 100vh
  justify-content center
  flex-wrap wrap
  &.gray
    background-color #F6F6F6
    padding-top 100px
    margin-bottom -80px
  &.main
    padding-top 180px


  .section-description
    padding-top 100px
    width 450px
    margin-right 100px
    h1
      font-weight bold
    p
      font-weight 500

  .slider
    display flex
    flex-direction column
    width 1080px
    h1
      font-weight 300
      padding 90px 0
</style>
