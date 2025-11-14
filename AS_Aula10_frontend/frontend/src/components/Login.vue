<template>
  <div class="min-vh-100" style="background: #baf6fc; margin: 0; padding: 0;">
    <nav class="navbar navbar-light px-4 w-100 m-0" style="background: #8ee3ef; position: absolute; top: 0; left: 0; right: 0;">
      <span class="navbar-brand mb-0 h1">
        <i class="bi bi-car-front-fill" style="font-size: 2rem;"></i>
      </span>
      <div class="d-flex align-items-center gap-3">
        <a href=""><i class="bi bi-whatsapp"  style="font-size: 1.5rem;"></i></a>
        <a href=""><i class="bi bi-instagram" style="font-size: 1.5rem;"></i></a>
        <a href=""><i class="bi bi-geo-alt" style="font-size: 1.5rem;"></i></a>
        <a href=""><i class="bi bi-telephone" style="font-size: 1.5rem;"></i></a>
        <i class="bi bi-person-circle" style="font-size: 2rem;"></i>
      </div>
    </nav>
    <div class="d-flex justify-content-center align-items-center" style="height: 80vh;">
      <div class="bg-white p-5 rounded shadow" style="min-width: 350px;">
        <h4 class="mb-4 text-center">Por favor entre em<br>sua conta!</h4>
        <form v-on:submit.prevent="addproduto">
          <div class="mb-3">
            <input type="text" class="form-control" v-model="user.username" placeholder="Usuário" required />
          </div>
          <div class="mb-3">
            <input type="password" class="form-control" v-model="user.senha" placeholder="Senha" required />
          </div>

          <button class="btn btn-primary w-100 mb-2" type="submit">ENTRAR</button>

        </form>
      </div>
    </div>
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
            localStorage.setItem('lastLogin', Date.now().toString())
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
