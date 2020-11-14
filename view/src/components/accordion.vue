<script>
export default {
  name: 'accordion',
  functional: true,
  render(createElement, context) {
    const data = {
      props: {
        name: 'expand'
      },
      on: {
        afterEnter(element) {
          element.style.height = 'auto'
        },
        enter(element) {
          const { width } = getComputedStyle(element)
          element.style.width = width
          element.style.position = 'absolute'
          element.style.visibility = 'hidden'
          element.style.height = 'auto'
          const { height } = getComputedStyle(element)
          element.style.width = null
          element.style.position = null
          element.style.visibility = null
          element.style.height = 0
          getComputedStyle(element).height;
          requestAnimationFrame(_ => {
            element.style.height = height
          })
        },
        leave(element) {
          const { height } = getComputedStyle(element)
          element.style.height = height
          getComputedStyle(element).height
          requestAnimationFrame(_ => {
            element.style.height = 0
          })
        }
      }
    }
    return createElement('transition', data, context.children)
  }
}
</script>

<style lang="stylus" scoped>
*
  will-change height
  transform translateZ(0)
  backface-visibility hidden
  perspective 1000px
</style>

<style lang="stylus">
.expand-enter-active,
.expand-leave-active
  transition height .2s ease
  overflow hidden

.expand-enter,
.expand-leave-to
  height 0
</style>