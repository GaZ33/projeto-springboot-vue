<template>
  <div id='delete-produto'>
    <h1>Delete Produto</h1>
    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar para a lista de produtos</router-link
      >
    </p>
    <form v-on:submit.prevent='deleteproduto'>
      <div class='form-group'>
        <label name='produto_idProduto'>ID</label>
        <input
          type='text'
          class='form-control'
          disabled
          v-model='produto.idProduto'
          id='produto_idProduto'
        />
      </div>

      <div class='form-group'>
        <label name='produto_nomeProduto'>Nome</label>
        <input
          type='text'
          class='form-control'
          v-model='produto.nomeProduto'
          id='produto_nomeProduto'
          disabled
        />
      </div>

      <div class='form-group'>
        <label name='produto_precoProduto'>Preço</label>
        <input
          type='text'
          class='form-control'
          v-model='produto.precoProduto'
          id='produto_precoProduto'
          disabled
        />
      </div>

      <div class='form-group'>
        <label name='produto_quantidadeProduto'>Quantidade</label>
        <input
          type='text'
          class='form-control'
          v-model='produto.quantidadeProduto'
          id='produto_quantidadeProduto'
          disabled
        />
      </div>

      <div class='form-group'>
        <button class='btn btn-primary'>Delete</button>
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
  created: function () {
    this.getprodutoData()
  },
  methods: {
    getprodutoData: function () {
      this.$http
        .get('http://localhost:8080/produto/' + this.$route.params.idProduto)
        .then(
          (response) => {
            this.produto.idProduto = this.$route.params.idProduto
            this.produto.nomeProduto = response.body['nomeProduto']
            this.produto.precoProduto = response.body['precoProduto']
            this.produto.quantidadeProduto = response.body['quantidadeProduto']
            this.$forceUpdate()
          },
          (response) => {}
        )
    },
    deleteproduto: function () {
      this.$http.get('http://localhost:8080/remover/' + this.produto.idProduto).then(
        (response) => {
          this.produto = {}
          alert(response.body['mensagem'])
          this.$router.push('list')
        },
        (response) => {
          alert(response.body['mensagem'])
        }
      )
    }
  }
}
</script>
