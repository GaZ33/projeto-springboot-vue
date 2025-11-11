<template>
  <div id="create-produto">
    <h1>Create Produto</h1>

    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar para a lista de produtos</router-link
      >
    </p>
    <form v-on:submit.prevent="addproduto">
      <div class="form-group">
        <label name="produto_nomeProduto">Nome</label>
        <input
          type="text"
          class="form-control"
          v-model="produto.nomeProduto"
          id="produto_nomeProduto"
          required
        />
      </div>

      <div class="form-group">
        <label name="produto_precoProduto">Preço</label>
        <input
          type="text"
          class="form-control"
          v-model="produto.precoProduto"
          id="produto_precoProduto"
          required
        />
      </div>

      <div class="form-group">
        <label name="produto_quantidadeProduto">Quantidade</label>
        <input
          type="text"
          class="form-control"
          v-model="produto.quantidadeProduto"
          id="produto_quantidadeProduto"
          required
        />
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
      produto: {}
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
