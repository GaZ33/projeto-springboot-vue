<template>
  <div id="create-produto">
    <h1>LOGIN</h1>

    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar</router-link
      >
    </p>
    <form v-on:submit.prevent="addproduto">
      <div class="form-group">
        <label name="produto_nomeProduto">Nome</label>
        <input
          type="text"
          class="form-control"
          v-model="user.username"
          id="produto_nomeProduto"
          required
        />
      </div>

      <div class="form-group">
        <label name="produto_quantidadeProduto">Senha</label>
        <input
          type="password"
          class="form-control"
          v-model="user.senha"
          id="produto_quantidadeProduto"
          required
        />
      </div>

      <div class="form-group">
        <button class="btn btn-primary">Entrar</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: {}
    }
  },
  methods: {
    addproduto: function () {
      var username = this.user.username.trim()
      var password = this.user.senha.trim()

      this.$http
        .post('http://localhost:8080/api/auth/login', {
          username: username,
          password: password
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
        .then(
          (response) => {
            this.produto = {}
            console.log(response)
            alert(response.body['token'])
            localStorage.setItem('token', response.body['token'])
            this.$router.push('list')
          },
          (response) => {
            console.log(response)
            alert(response.body['token'])
          }
        )
    }
  }
}
</script>
