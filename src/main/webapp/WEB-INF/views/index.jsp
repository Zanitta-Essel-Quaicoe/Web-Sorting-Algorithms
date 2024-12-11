<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sorting Algorithms Interface</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #74ebd5, #9face6);
            color: #333;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #4CAF50;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .buttons {
            display: flex;
            justify-content: space-between;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #45a049;
        }
        .output {
            margin-top: 20px;
        }
        .output ul {
            list-style-type: none;
            padding: 0;
        }
        .output ul li {
            padding: 10px;
            background: #f4f4f4;
            border: 1px solid #ddd;
            margin-bottom: 5px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Advanced Sorting Algorithms</h1>

        <div class="form-group">
            <label for="number">Add a Number:</label>
            <input type="number" id="number" placeholder="Enter a number">
            <button id="add-btn">Add Number</button>
        </div>

        <div class="form-group">
            <label for="index">Update a Number:</label>
            <input type="number" id="index" placeholder="Index">
            <input type="number" id="new-value" placeholder="New Value">
            <button id="update-btn">Update</button>
        </div>

        <div class="form-group">
            <label for="algorithm">Choose Sorting Algorithm:</label>
            <select id="algorithm">
                <option value="quicksort">Quick Sort</option>
                <option value="mergesort">Merge Sort</option>
                <option value="heapsort">Heap Sort</option>
                <option value="radixsort">Radix Sort</option>
            </select>
            <button id="sort-btn">Sort Numbers</button>
        </div>

        <div class="form-group">
            <label for="delete-index">Delete a Number:</label>
            <input type="number" id="delete-index" placeholder="Index">
            <button id="delete-btn">Delete</button>
        </div>

        <div class="output">
            <h3>Numbers:</h3>
            <ul id="numbers-list"></ul>
        </div>
    </div>

    <script>
        const apiUrl = 'http://localhost:8080/AdvancedWebSortingAlgorithms/sorting';

        const numbersList = document.getElementById('numbers-list');
        const numberInput = document.getElementById('number');
        const addButton = document.getElementById('add-btn');
        const updateIndexInput = document.getElementById('index');
        const newValueInput = document.getElementById('new-value');
        const updateButton = document.getElementById('update-btn');
        const algorithmSelect = document.getElementById('algorithm');
        const sortButton = document.getElementById('sort-btn');
        const deleteIndexInput = document.getElementById('delete-index');
        const deleteButton = document.getElementById('delete-btn');

        // Helper function to update the list display
        function updateNumbersList(numbers) {
            numbersList.innerHTML = '';
            numbers.forEach((num, index) => {
                const li = document.createElement('li');
                li.textContent = `${index}: ${num}`;
                numbersList.appendChild(li);
            });
        }

        // Add number
        addButton.addEventListener('click', async () => {
            const number = parseInt(numberInput.value);
            if (!isNaN(number)) {
                const response = await fetch(`${apiUrl}/add`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(number)
                });
                const data = await response.json();
                updateNumbersList(data.data);
                numberInput.value = '';
            }
        });

        // Update number
        updateButton.addEventListener('click', async () => {
            const index = parseInt(updateIndexInput.value);
            const newValue = parseInt(newValueInput.value);
            if (!isNaN(index) && !isNaN(newValue)) {
                const response = await fetch(`${apiUrl}/update`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify([index, newValue])
                });
                const data = await response.json();
                updateNumbersList(data.data);
                updateIndexInput.value = '';
                newValueInput.value = '';
            }
        });

        // Sort numbers
        sortButton.addEventListener('click', async () => {
            const algorithm = algorithmSelect.value;
            const response = await fetch(`${apiUrl}/${algorithm}`, {
                method: 'POST'
            });
            const data = await response.json();
            updateNumbersList(data.data);
        });

        // Delete number
        deleteButton.addEventListener('click', async () => {
            const index = parseInt(deleteIndexInput.value);
            if (!isNaN(index)) {
                const response = await fetch(`${apiUrl}/delete`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(index)
                });
                const data = await response.json();
                updateNumbersList(data.data);
                deleteIndexInput.value = '';
            }
        });
    </script>
</body>
</html>
