<template>
  <div class="slider-layout">
    <div class="slide-card" v-for="(slide, i) in slidersComputed" :key="slide.step" :class="{ 'active': i === 1 }">
      <div class="image">
        <component :is="slide.img"/>
      </div>
      <span class="step">
        {{slide.step}}
      </span>
      <p class="description">
        {{slide.description}}
      </p>
    </div>
    
    
    <div class="next" @click="next()" key="next"><b-icon-chevron-right/></div>
    <div class="prev" @click="prev()" key="prev"><b-icon-chevron-left/></div>
  </div>
</template>

<script>
import times from '@/icons/times'
import protect from '@/icons/protect'
import price from '@/icons/price'
export default {
  data () {
    return {
      slides: [
        {
          step: 'Шаг 4',
          description: 'Загрузить обработаную базу данных',
          img: 'price'
        },
        {
          step: 'Шаг 1',
          description: 'Загрузите подготовленный файл для обработки в формате .xlsx или .csv',
          img: 'times'
        },
        {
          step: 'Шаг 2',
          description: 'Посмотрите результат обработки адресов',
          img: 'protect'
        },
        {
          step: 'Шаг 3',
          description: 'При необходимости отредактируйте некорректные адреса',
          img: 'price'
        }
      ],
      buffer: {
        step: 'Шаг 3',
        description: 'При необходимости отредактируйте некорректные адреса',
        img: 'price'
      }
    }
  },
  methods: {
    next () {
      const temp = this.slides.shift()
      this.slides.push(temp)
    },
    prev () {
      const temp = this.slides.pop()
      this.slides.unshift(temp)
    }
  },
  computed: {
    slidersComputed () {
      return this.slides.slice(0, 3)
    }
  },
  components: {
    times,
    protect,
    price
  }
}
</script>

<style lang="stylus" scoped>
.slider-layout
  display flex
  width 100%
  min-height 300px
  justify-content space-between
  position relative

  .next, .prev
    width 50px
    height 50px
    display flex
    border-radius 50%
    position absolute
    top 30%
    background #D3EAFF
    cursor pointer
    &:hover
      background #0061BF
    
    svg
      width 50%
      height 50%
      margin auto
      fill white
  .next
    right -15px

  .prev
    left -15px
  

  .slide-card
    min-width 300px
    width 300px
    display flex
    flex-shrink 0
    flex-direction column
    align-items center
    transform scale(.9)
    transform-origin center 40%
    transition transform .3s ease
    &.active
      transform scale(1.1)
      .step, .description
        opacity 1


    .image
      width 300px
      height 300px
      display flex
      box-shadow 0px 0px 30px rgba(0, 0, 0, 0.1)
      border-radius 10px
      background-color #FFFFFF
      svg
        margin auto
        height 250px
        width 250px
    .step, .description
      ransition opacity .2s ease
      opacity 0
      width 80%
      text-align center
      font-weight 500
    
    .step
      padding-top 15px
      color #0055A6
    
    .description
      padding-top 5px
      color #8c8c8c

</style>