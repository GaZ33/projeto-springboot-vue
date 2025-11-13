<template>
  <div id="list-produtos">
    <h1>List Produtos</h1>
    <p>
      <router-link :to="{ name: 'agendamentos' }" class="btn btn-primary">
        Ver agendamentos
      </router-link>
    </p>
    <p>
      <router-link :to="{ name: 'login' }" class="btn btn-primary">
        Login page
      </router-link>
    </p>
    <p>
      <router-link :to="{ name: 'criarUsuario' }" class="btn btn-primary">
        Criar usuario
      </router-link>
    </p>
    <p>
      <router-link :to="{ name: 'criarAgendamentos' }" class="btn btn-primary">
        Criar agendamentos
      </router-link>
    </p>
    <p>
      <router-link :to="{ name: 'criarAlunos' }" class="btn btn-primary">
        Criar alunos
      </router-link>
    </p>
    <table class="table table-hover">
      <thead>
        <tr>
          <td>ID</td>
          <td>Nome</td>
          <td>Preço</td>
          <td>Quantidade</td>
        </tr>
      </thead>

      <tbody>
        <tr v-for='produto in produtos' v-bind:key='produto'>
          <td>{{ produto.idProduto}}</td>
          <td>{{ produto.nomeProduto }}</td>
          <td>{{ produto.precoProduto }}</td>
          <td>{{ produto.quantidadeProduto }}</td>
          <td>
            <router-link
              :to="{ name: 'update', params: { idProduto: produto.idProduto} }"
              class="btn btn-primary">
              Update
            </router-link>
            <router-link
              :to="{ name: 'delete', params: { idProduto: produto.idProduto} }"
              class="btn btn-danger">
              Delete
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      produtos: []
    }
  },

  created: function () {
    this.fetchprodutoData()
  },

  methods: {
    fetchprodutoData: function () {
      this.$http.get('http://localhost:8080/produtos').then(
        (response) => {
          this.produtos = response.body
        },
        (response) => {}
      )
    }
  }
}
</script>
