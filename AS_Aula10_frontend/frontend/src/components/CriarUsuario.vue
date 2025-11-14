<template>
  <div class="min-vh-100" style="background: #baf6fc; margin: 0; padding: 0;">
    <nav class="navbar navbar-light px-4 w-100 m-0" style="background: #8ee3ef; position: absolute; top: 0; left: 0; right: 0;">
      <span class="navbar-brand mb-0 h1">
        <i class="bi bi-car-front-fill" style="font-size: 2rem;"></i>
      </span>
      <div class="d-flex align-items-center gap-3">
        <i class="bi bi-whatsapp" style="font-size: 1.5rem;"></i>
        <i class="bi bi-instagram" style="font-size: 1.5rem;"></i>
        <i class="bi bi-geo-alt" style="font-size: 1.5rem;"></i>
        <i class="bi bi-telephone" style="font-size: 1.5rem;"></i>
        <i class="bi bi-person-circle" style="font-size: 2rem;"></i>
      </div>
    </nav>
    <div class="d-flex justify-content-center align-items-center" style="height: 80vh;">
      <div class="bg-white p-5 rounded shadow" style="min-width: 350px;">
        <h4 class="mb-4 text-center">Criar usuário</h4>
        <form v-on:submit.prevent="addproduto">
          <div class="mb-3">
            <input type="text" class="form-control" v-model="usuario.nomeUsuario" placeholder="Username" required />
          </div>
          <div class="mb-3">
            <input type="password" class="form-control" v-model="usuario.senha" placeholder="Senha" required />
          </div>
          <div class="mb-3">
            <input type="email" class="form-control" v-model="usuario.email" placeholder="Email" required />
          </div>
          <div class="mb-3">
            <select class="form-control" v-model="usuario.tipoUsuario" required>
              <option value="3">ADMIN</option>
              <option value="2">INSTRUTOR</option>
              <option value="1">ALUNO</option>
            </select>
          </div>
          <button class="btn btn-primary w-100 mb-2" type="submit">Create</button>
          <div class="text-center">
            <small>
              <router-link :to="{ name: 'list' }">Voltar para a lista</router-link>
            </small>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      usuario: {}
    }
  },
  mounted () {
    const lastLogin = localStorage.getItem('lastLogin')
    const now = Date.now()
    if (!lastLogin || (now - parseInt(lastLogin, 10)) > 3600000) {
      this.$router.push({ name: 'login' })
      return
    }
  },
  methods: {
    addproduto: function () {
      // Validation
      // var quantidadeProduto = parseFloat(this.produto.quantidadeProduto)
      var username = this.usuario.nomeUsuario.trim()
      var password = this.usuario.senha.trim()
      var email = this.usuario.email.trim()
      var tipoUsuario = this.usuario.tipoUsuario
      var token = localStorage.getItem('token')
      // if (isNaN(quantidadeProduto)) {
      //   alert('Quantidade deve ser um número')
      //   return false
      // } else {
      //   this.produto.quantidadeProduto = this.produto.quantidadeProduto
      // }

      this.$http
        .post('http://localhost:8080/api/usuarios/cadastro', {
          username: username,
          password: password,
          email: email,
          tipoUsuario: tipoUsuario
        }, {
          headers: {
            'Authorization': 'Bearer ' + token
          }
        })
        .then(
          (response) => {
            this.produto = {}
            console.log(response)
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
