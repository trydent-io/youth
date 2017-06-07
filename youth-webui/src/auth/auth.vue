<!--suppress HtmlUnknownTag -->
<template>
  <div class="ui middle aligned center aligned grid">
    <div class="column">
      <h2 class="ui teal image header">
        <img src="static/logo_youth.png" class="image">
        <div class="content" style="color: #34495e">
          Log-in to your account
        </div>
      </h2>
      <form class="ui large form">
        <div class="ui stacked segment">
          <div class="field">
            <div class="ui left icon input">
              <i class="user icon"></i>
              <input type="text" name="email" placeholder="E-mail address">
            </div>
          </div>
          <div class="field">
            <div class="ui left icon input">
              <i class="lock icon"></i>
              <input type="password" name="password" placeholder="Password">
            </div>
          </div>
          <div class="ui fluid large teal submit button">Login</div>
        </div>

        <transition type="fade">
          <div class="ui error message"></div>
        </transition>

      </form>

      <auth-social :type="type"></auth-social>

      <h4 class="ui dividing header">Social Login</h4>
      <div class="ui fluid large buttons">
        <a class="ui google plus icon button" href="http://localhost:8080/auth/google" target="_blank">
          <i class="google icon"></i>
          Google
        </a>
        <div class="or"></div>
        <a class="ui facebook icon button" href="http://localhost:8080/auth/facebook" target="_blank">
          <i class="facebook icon"></i>
          Facebook
        </a>
      </div>

      <div class="ui message">
        New to us? <a href="#">Sign Up</a>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'
  import axios from 'axios'
  import AuthSocial from './auth-social.vue'
  import * as commands from './auth-social-command'

  export default {
    components: {AuthSocial},
    mounted () {
      $('.ui.form')
        .form({
          fields: {
            email: {
              identifier: 'email',
              rules: [
                {
                  type: 'empty',
                  prompt: 'Please enter your e-mail'
                },
                {
                  type: 'email',
                  prompt: 'Please enter a valid e-mail'
                }
              ]
            },
            password: {
              identifier: 'password',
              rules: [
                {
                  type: 'empty',
                  prompt: 'Please enter your password'
                },
                {
                  type: 'length[6]',
                  prompt: 'Your password must be at least 6 characters'
                }
              ]
            }
          }
        })
    },
    data: () => ({
      type: 'google'
    }),
    methods: {
      socialLogin (social) {
        this.type = social
        this.$bus.$emit(commands.OPEN_SOCIAL_LOGIN)
      },
      openGoogle () {
        this.socialLogin('google')
      },
      openFacebook () {
        axios.interceptors.request.use(config => {
          console.log(`Config: ${config.url}`)
          return config
        })

        axios.get(`http://localhost:8080/auth/facebook`)
          .then(res => {
            console.log(`Res: ${res}`)
            console.log(`Data: ${res.data}`)

            return res
          })
          .catch(err => {
            console.log(`Err: ${err}`)
            console.log(`Data: ${err.statusCode}`)
          })
//        this.socialLogin('facebook')
      }
    }
  }
</script>

<style>
  body {
    background: url(/static/back00.png) center top no-repeat !important;
  }

  body > .grid {
    height: 100%;
  }

  .image {
    margin-top: -100px;
  }

  .column {
    max-width: 450px;
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s
  }

  .fade-leave-active {
    position: absolute;
    z-index: -4;
  }

  .fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */
  {
    opacity: 0
  }
</style>
