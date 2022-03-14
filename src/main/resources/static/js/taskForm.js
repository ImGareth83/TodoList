newItemForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const productsControl = new ProductsController();
    const newTitle = document.querySelector('#title');
    const newDescription = document.querySelector('#description');
    const newTargetDateTime = document.querySelector('#targetDateTime');

    productsControl.addItem(newTitle.value, newDescription.value, newTargetDateTime.value);

    // Clear the form
    newTitle.value = '';
    newDescription.value = '';
    newTargetDateTime.value = '';

});


document.querySelector('#deleteTask').addEventListener('click', event => {
    event.preventDefault();


})