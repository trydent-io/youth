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
          <h4 class="ui dividing header">Social Login</h4>
          <div class="ui fluid large buttons">
            <button class="ui google plus icon button">
              <i class="google icon"></i>
              Google
            </button>
            <div class="or"></div>
            <button class="ui facebook icon button">
              <i class="facebook icon"></i>
              Facebook
            </button>
          </div>
        </div>

        <transition type="fade">
          <div class="ui error message"></div>
        </transition>

      </form>

      <div class="ui message">
        New to us? <a href="#">Sign Up</a>
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'

  export default {
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
