//Doing a Product web app, in product page to 
//display the name, description, imageUrl, style, price, ..., ...,.....,....


const createHTMLList = (index, title, description, targetDateTime) =>
    `
                        <tr>
                            <td>${index} ${title}</td>
                            <td>${description}</td>
                            <td>${targetDateTime}</td>
                            <input type="hidden" name="id" value="${index}"/>
                            <td><a id="deleteTask" href="http://localhost:8080/tasks/delete/${index}">Delete</a></td>
                        </tr>

`;


function displayProductDetails(item) {
    document.querySelector("#modalName").innerText = item.title;
    document.querySelector("#modalImg").src = item.description;
    document.querySelector("#modalStyle").innerText = item.targetDateTime;

}


class ProductsController {
    constructor() {
        this._items = [];       //create an array to store the details of product items
    }

    //method to add the items into the array
    addItem(title, description, targetDateTime) {
        //fetch POST HTTP method to send the data to the backend via the API
        var productController = this;
        const formData = new FormData();
        formData.append('title', title);
        formData.append('description', description);
        formData.append('targetDateTime', targetDateTime);

        fetch('http://localhost:8080/tasks/add', {
            method: 'POST',
            body: formData
        })
            .then(function(response) {
                console.log(response.status); // Will show you the status
                if (response.ok) {
                    alert("Successfully Added Product!")
                }
                else{
                    alert("Error adding item to Product")
                }

            })
            .catch((error) => {
                console.error('Error:', error);
                alert("Error adding item to Product")
            });
    }

    displayItem() {
        //fetch GET HTTP method (default) the items from the database using the API
        var productController = this;
        productController._items = [];

        //fetch data from database using the REST API endpoint from Spring Boot
        fetch('http://localhost:8080/tasks/all')
            .then((resp) => resp.json())
            .then(function (data) {
                data.forEach(function (item, index) {

                    const itemObj = {
                        id: item.id,
                        title: item.title,
                        description: item.description,
                        targetDateTime: item.targetDateTime,
                    };
                    productController._items.push(itemObj);
                });

                productController.renderProductPage();

            })
            .catch(function (error) {
                console.log(error);
            });
    }

    renderProductPage() {
        var productHTMLList = [];

        for (var i = 0; i < this._items.length; i++) {
            const item = this._items[i];            //assign the individual item to the variable

            const productHTML = createHTMLList(item.id, item.title, item.description, item.targetDateTime);
            productHTMLList.push(productHTML);
        }

        //Join all the elements/items in my productHTMLList array into one string, and seperate by a break
        const pHTML = productHTMLList.join('\n');
        document.querySelector('#row').innerHTML = pHTML;


        //addEventListener - click
        for (var i = 0; i < this._items.length; i++) {
            const item = this._items[i];
            document.getElementById(i).addEventListener("click", function () {
                displayProductDetails(item);
            });
        }

    }


}   //End of ProductsController class
