<template>
	<div class="fullscreen">
		<b-overlay :show="loading" class="overlay-layout" :class="{ 'opacity': showSettings }" spinner-type="grow" spinner-variant="primary">
		<div
				class="dropzone"
				:class="{ 'draghover': dragHover,
									'ready': showDropZone }"
				@drop.prevent="handleDrop"
        @dragenter.stop="handleDragOver"
        @dragleave.prevent
        @dragover.prevent>

				<div class="dropzone-white">
					<b-icon-sliders class="ml-1 settins" :class="{ 'disabled': !file.data }" @click="toggleSettings"/>
					<label class="drop-border">
						<div class="drop-mark" :class="{ 'filled': file.data }">{{ fileName }}</div>
						<input type="file"
								id="file"
								accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
								hidden
								@change="setFile"/>
					</label>

				</div>
				<div class="dropzone-blue">
					<div class="drop-description">
						<label for="file"><h4><b-icon-file-earmark-plus/> Загрузите файл </h4></label>
						<div class="drop-devider"><span/> <p>или</p> <span/></div>
						<b-form-input
							size="lg"
							class="inp-theme-whiteblue"
							v-model="file.link"
							type="text"
							placeholder="Вставьте URL"/>
					</div>
				</div>
		</div>
		<collapse>
		<b-row align-h="center" v-if="file.data">
				<b-button size="lg"
						class="btn-theme-blue mt-5 py-2 px-5"
						@click="upload()">
						Продолжить
				</b-button>
		</b-row>
		</collapse>
		</b-overlay>
		<transition name="fade">
			<pSettings :file="file.data" v-if="showSettings" @close="toggleSettings" @setFileScheme="addConfig"/>
		</transition>
	</div>
</template>

<script>
import collapse from './accordion'
import pSettings from './p-settings'
export default {
  data () {
    return {
			showDropZone: false,
			dragHover: false,
			loading: false,
			showSettings: false,
			file: {
				data: null,
				config: null,
				link: '',
        uploaded: 0,
        error: false,
        responseReceived: false,
        get showProgress () { return this.uploaded < 100 || !this.responseReceived }
      },
    }
	},
	created () {
		document.addEventListener('dragenter', this.handleDragEnter)
	},
	beforeDestroy () {
		document.removeEventListener('dragenter', this.handleDragEnter)
	},
  methods: {
		toggleSettings () {
			this.showSettings = !this.showSettings
		},
		handleDragEnd (e) {
			this.showDropZone = false
		},
		handleDragEnter (e) {
			this.showDropZone = true
		},

		handleDragOver () {
      this.dragHover = true
    },
		
    handleDrop (e) {
			this.dragHover = false
			this.showDropZone = false
			const [data] = [...e.dataTransfer.files]
			this.file.data = data
		},
		upload () {
      this.loading = true
      this.$bs.uploadFileAsync(this.file)
        .then(({ bad, good, middle, total }) => {
					this.$root.fileName = this.file.data.name
					const counts = {
						total,
						done: good,
						errors: bad,
						verify: middle
					}
					setTimeout(() => {
						this.loading = false
						this.$emit('uploaded', counts)
          }, 1000)
				})
		},
		addConfig (conf) {
			this.file.config = conf
		},
		setFile (e) {
			const [file] = [...e.target.files]
			this.file.data = file
		}
	},
	computed: {
		fileName () {
			return (this.file.data || {}).name || 'Положите файл сюда'
		}
	},
	components: {
		collapse,
		pSettings
	}
}
</script>

<style lang="stylus" scoped>
.fullscreen
	display flex
	height 80vh

.overlay-layout
	display flex
	flex-direction column
	margin auto
	&.opacity
		opacity 0

.dropzone
	overflow hidden
	margin auto
	display flex
	flex-wrap wrap
	width 900px
	height 350px
	background #FFFFFF
	box-shadow 0px 0px 30px rgba(0, 0, 0, 0.1)
	border-radius 10px
	transition box-shadow .15s ease
	&.draghover
		.dropzone-white
			.drop-border
				transform scale(1.1)
	&.ready
		box-shadow 0px 0px 30px #005fbc5c
	.dropzone-white
		display flex
		flex 1
		min-width 300px
		height 100%
		pointer-events none
		position relative
		.settins
			position absolute
			top 30px
			left 30px
			pointer-events all
			cursor pointer
			opacity 1
			fill #0055A6
			transition opacity .2s ease
			&.disabled
				opacity 0

		&:hover
			.drop-border
				transform scale(1.1)
		.drop-border
			padding 10px
			margin auto
			display flex
			min-width 200px
			min-height 150px
			width 70%
			height 50%
			border 2px dashed #0055a620
			border-radius 6px
			transition transform .2s ease
			.drop-mark
				display flex
				justify-content center
				align-items center
				width 100%
				height 100%
				background none
				border-radius 4px
				color gray
				font-weight 500
				pointer-events all
				cursor pointer
				transition background .15s ease, color .15s ease
				&.filled
					background linear-gradient(263.82deg, #0064C5 4.04%, #004C9B 95.62%)
					color white

	.dropzone-blue
		display flex
		flex 1
		min-width 300px
		background-color #005FBC
		box-shadow 0px 0px 30px rgba(0, 0, 0, 0.1)
		height 100%
		color white
		.drop-description
			margin auto
			display flex
			flex-direction column
			justify-content space-between
			align-items center
			min-width 200px
			min-height 150px
			width 70%
			height 50%
			label
				&:hover
					opacity .8
					cursor pointer
		.drop-devider
			display flex
			width 100%
			justify-content center
			align-items center
			p
				margin-bottom 4px
			span
				display block
				width 20%
				margin 0 20px
				height 1px
				background-color #E6E6E6
		input
			pointer-events all

.fade-enter-active,
.fade-leave-active
	transition opacity .5s

.fade-enter,
.fade-leave-to
	opacity 0
</style>
