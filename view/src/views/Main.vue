<template>
  <b-container fluid class="h-100">
    <pHeader />
    <pUploader @uploaded="showPreview"/>
    <pFooter/>
  </b-container>
</template>
<script>
import pHeader from '@/components/p-header'
import pUploader from '@/components/p-uploader'
import pFooter from '@/components/p-footer'
export default {
  data () {
    return {
    }
  },
  methods: {
    showPreview (data) {
      this.$router.push({ path: 'preview', query: { ...data } })
    }
  },
  computed: {
  },
  components: {
    pHeader,
    pUploader,
    pFooter
	}
}
</script>
<style lang="stylus">
</style>
