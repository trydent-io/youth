<!--suppress HtmlUnknownTag -->
<template>
  <md-whiteframe v-for="(p, index) in persons" :key="p.uuid" md-elevation="2" md-with-hover style="width: 100%; border-radius: 2px; background: #fff">
    <md-layout md-gutter style="margin: 8px 0 8px 0">
      <md-layout md-flex="10">
        <md-avatar class="md-large">
          <img src="/static/someone.jpg" alt="People">
        </md-avatar>
      </md-layout>
      <md-layout md-flex="20" style="justify-content: flex-start; align-items: center">
        <table>
          <tr>
            <td>
              <span class="md-title" style="color: #7f8c8d">{{ fullName(p) }}</span>
            </td>
          </tr>
          <tr>
            <td>
              <span class="md-subheading" style="color: #95a5a6">luke.skywalker@email.com</span>
            </td>
          </tr>
        </table>
      </md-layout>
      <md-layout style="justify-content: flex-start; align-items: center">
        <md-chip style="background: rgb(26, 188, 156); color: rgb(236, 240, 241)">{{p.gender}}</md-chip>
      </md-layout>
      <md-layout style="justify-content: flex-end; align-items: center">
        <md-button :class="{ 'md-icon-button': true, 'md-accent': p.liked === 1}"
                   @click.native="liked(index)">
          <md-icon>favorite</md-icon>
        </md-button>
        <md-button class="md-icon-button md-primary" @click.native="openPerson(index)">
          <md-icon>editing</md-icon>
        </md-button>
        <md-button class="md-icon-button md-warn" @click.native="removing(index)">
          <md-icon>delete</md-icon>
        </md-button>
      </md-layout>
    </md-layout>
  </md-whiteframe>
</template>
