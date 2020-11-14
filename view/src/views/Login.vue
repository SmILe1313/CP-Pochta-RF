<template>
	<b-container fluid class="fullscreen">
		<pHeader/>
		<b-container class="mt-3 h-100" fluid="sm">

			<b-row align-h="center" class="p-5">
				<h2 class="text-center font-weight-normal"> Вход </h2>
			</b-row>

			<b-row align-h="center" class="p-2">
				<b-form-input
						size="lg"
						class="w-50 inp-theme-blue"
						id="login"
						v-model="form.login"
						type="text"
						placeholder="Эл. почта или телефон +7-ХХХ-ХХХ-ХХ-ХХ"
						required/>
			</b-row>

			<b-row align-h="center" class="p-2">
				<b-form-input
						size="lg"
						class="w-50 inp-theme-blue"
						id="password"
						v-model="form.password"
						type="password"
						placeholder="Пароль"
						required/>
			</b-row>

			<b-row align-h="center" class="p-4">
				<b-button class="btn-theme-blue px-5 py-2" @click="onSubmit">Войти</b-button>
			</b-row>

			<b-row align-h="center" class="p-1">
				<b-row align-h="between" class="w-50">
					<b-link href="#/help" class="text-theme-blue px-5"> Не помню пароль </b-link>
					<b-link href="#/help" class="text-theme-blue px-5"> Зарегистрироваться </b-link>
				</b-row>
			</b-row>

			<b-row align-h="center" class="px-5 mt-4">
				<b-row align-h="between" class="w-75 px-5 mt-4">
					<hr class="w-100 px-5"/>
				</b-row>
			</b-row>

			<!-- <b-row align-h="center">
				<b-link href="#/help" class="text-theme-blue p-4"><esia/> Войти через Госуслуги </b-link>
			</b-row> -->

		</b-container>
		<pFooter/>
	</b-container>
</template>

<script>
import pHeader from '@/components/p-header'
import pFooter from '@/components/p-footer'
import esia from '@/icons/logoEsia'
export default {
  data () {
    return {
			form: {
				login: '',
				password: '',
				token: '',
				key: ''
			}
    }
  },
  methods: {
		onSubmit (e) {
			e.preventDefault()
			console.log(this.form)
			this.$bs.doLogin(this.form.login, this.form.password)
				.then(() => {
					this.$router.push('/')
				})
		}
	},
	components: {
		pHeader,
		esia,
		pFooter
	}
}
</script>

<style lang="stylus" scoped>
.fullscreen
	height 80vh
</style>
