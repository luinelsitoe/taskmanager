document.addEventListener("DOMContentLoaded", function () {
  const select = document.getElementById("filter-type");
  const forms = {
    status: document.getElementById("status-form"),
    title: document.getElementById("title-form"),
    interval: document.getElementById("interval-form"),
  };

  select.addEventListener("change", function () {
    // Esconde todos os formulários
    Object.values(forms).forEach((form) => (form.style.display = "none"));

    // Mostra o formulário escolhido
    const selected = select.value;
    if (forms[selected]) {
      forms[selected].style.display = "block";
    }
  });
});
