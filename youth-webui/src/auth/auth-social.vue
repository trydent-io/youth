<template>
  <div ref="social" class="ui modal" style="width: 512px; height: 448px">
    <i class="close icon"></i>
    <div class="header">
      <i class="share icon"></i>
      Social Login
    </div>
    <iframe class="content" :src="socialUrl">
    </iframe>
  </div>
</template>

<script>
  import config from '../config'
  import $ from 'jquery'
  import * as commands from './auth-social-command'

  export default {
    props: ['type'],
    mounted () {
      this.$bus.$on(commands.OPEN_SOCIAL_LOGIN, () => this.social.modal('show'))
    },
    computed: {
      social () { return $(this.$refs.social) },
      socialUrl () {
        return `${config.baseUrl}/auth/${this.type}`
      }
    }
  }
</script>
