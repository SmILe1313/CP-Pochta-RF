<template>
  <b-container fluid class="h-100">
    <pHeader/>
    <pPreview/>
    <pFooter/>
  </b-container>
</template>
<script>
import pHeader from '@/components/p-header'
import pPreview from '@/components/p-preview'
import pFooter from '@/components/p-footer'
export default {
  data () {
    return {
    }
  },
  methods: {
  },
  computed: {
  },
  components: {
    pHeader,
    pPreview,
    pFooter
	}
}
</script>
<style lang="stylus">
</style>
