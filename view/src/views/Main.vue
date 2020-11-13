<template>
  <b-container fluid class="h-100">
    <pHeader />
    <pUploader/>
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
.custom-file-input:lang(ru)
  ~ .custom-file-label
    &::after
      content 'Просмотр'

</style>
