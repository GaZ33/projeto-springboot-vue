<template>
  <div id="create-produto">
    <h1>Criar usuario</h1>

    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar para a lista</router-link
      >
    </p>
    <form v-on:submit.prevent="addproduto">
      <div class="form-group">
        <label name="user_username">Username</label>
        <input
          type="text"
          class="form-control"
          v-model="usuario.nomeUsuario"
          id="user_username"
          required
        />
      </div>

      <div class="form-group">
        <label name="user_password">Senha</label>
        <input
          type="text"
          class="form-control"
          v-model="usuario.senha"
          id="user_password"
          required
        />
      </div>

      <div class="form-group">
        <label name="user_tipo">Tipo de usuario</label>
        <select name="user_tipo" id="user_tipo" v-model="usuario.tipoUsuario">
            <option value="ADMIN">ADMIN</option>
            <option value="INSTRUTOR">INSTRUTOR</option>
            <option value="ALUNO">ALUNO</option>
        </select>

      </div>

      <div class="form-group">
        <button class="btn btn-primary">Create</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      usuario: {}
    }
  },
  methods: {
    addproduto: function () {
      // Validation
      // var quantidadeProduto = parseFloat(this.produto.quantidadeProduto)
      var username = this.produto.nomeProduto.trim()
      var password = this.produto.quantidadeProduto.trim()
      alert(username + ' ' + password)
      // if (isNaN(quantidadeProduto)) {
      //   alert('Quantidade deve ser um número')
      //   return false
      // } else {
      //   this.produto.quantidadeProduto = this.produto.quantidadeProduto
      // }

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
