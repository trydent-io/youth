<!--suppress HtmlUnknownTag -->
<template>
  <div class="ui middle aligned center aligned grid" style="height: 100%">
    <div class="column" style="max-width: 450px">
      <!--<form class="ui large form">
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

      </form>-->

      <img src="static/space_porthole256.png" class="ui circular small centered image">
      <h2 class="ui teal image header">
        <span style="color: #f2f2f2">Welcome to Youth Demo App</span>
      </h2>
      <div class="ui pointing below label">
        Please login with your Open ID
      </div>
      <div class="ui vertical fluid large buttons">
        <a class="ui google plus icon labeled button" href="http://localhost:8080/auth/google" target="_self">
          <i class="google icon"></i>
          Google
        </a>
        <a class="ui facebook icon labeled button" href="http://localhost:8080/auth/facebook" target="_self">
          <i class="facebook icon"></i>
          Facebook
        </a>
        <a class="ui twitter icon labeled button" href="http://localhost:8080/auth/facebook" target="_self">
          <i class="twitter icon"></i>
          Twitter
        </a>
      </div>
      <br>
      <div class="ui circular label" :class="{ orange: !logged, olive: logged }"><i class="thumbs icon" :class="{ down: !logged, up: logged }" ></i> {{accessed}}</div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'
  import axios from 'axios'
  import jwtDecode from 'jwt-decode'

  export default {
    props: ['logged'],
    created () {
      if (this.logged) {
        axios.get('/auth/token')
          .then(res => {
            const token = res.data['token']

            if (token) {
              const decoded = jwtDecode(token)
              this.accessed = `${decoded.name} has logged!`

              this.$router.push('home')
            }
          })
      }
    },
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
      accessed: 'Not logged'
    })
  }
</script>

<style>
  body {
    background-color: #34495e !important;
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
