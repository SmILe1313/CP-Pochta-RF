<template>
  <b-container fluid class="h-100">
    <pHeader/>
    <pPreview v-bind="{ total, done, errors, verify }"/>
    <pFooter/>
  </b-container>
</template>
<script>
import pHeader from '@/components/p-header'
import pPreview from '@/components/p-preview'
import pFooter from '@/components/p-footer'
export default {
  props: {
    total: String,
    done: String,
    errors: String,
    verify: String
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
