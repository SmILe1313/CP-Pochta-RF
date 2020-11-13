<template>
  <b-container fluid>
    <b-container class="mt-3">
      <b-row align-v="center" align-h="between">
        <logo class="login-logo"/>
        <b-link href="#/help" class="text-theme-blue"><b-icon-question-circle size="sm"/> Помощь </b-link>
      </b-row>
    </b-container>
    <hr/>
  </b-container>
</template>

<script>
import logo from '@/icons/logo'
export default {
	components: {
		logo
	}
}
</script>