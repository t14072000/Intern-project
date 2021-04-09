const inputField1 = document.querySelector('.chosen-value1');
const dropdown1 = document.querySelector('.value-list1');
const dropdownArray1 = document.querySelectorAll('.value-list1 li');
const inputPlaceholder1 = inputField1.placeholder;

let valueArray1 = [];
dropdownArray1.forEach(item => {
  valueArray1.push(item.textContent);
});

let closeDropdown1 = () => {
  dropdown1.classList.remove('open');
}

inputField1.addEventListener('input', () => {
  dropdown1.classList.add('open');
  let inputValue1 = inputField1.value.toLowerCase();
  inputField1.value = inputField1.value.charAt(0).toUpperCase() + inputField1.value.slice(1);
  if (inputValue1.length > 0) {
    for (let j = 0; j < valueArray1.length; j++) {
      if (!(inputValue1.substring(0, inputValue1.length) === valueArray1[j].substring(0, inputValue1.length).toLowerCase())) {
        dropdownArray1[j].classList.add('closed');
      } else {
        dropdownArray1[j].classList.remove('closed');
      }
    }
  } else {
    for (let i = 0; i < dropdownArray1.length; i++) {
      dropdownArray1[i].classList.remove('closed');
    }
  }
});

dropdownArray1.forEach(item => {
  item.addEventListener('click', (evt) => {
    inputField1.value = item.textContent;
    dropdownArray1.forEach(dropdown1 => {
      dropdown1.classList.add('closed');
    });
  });
})

inputField1.addEventListener('focus', () => {
   inputField1.placeholder = 'Type to filter';
   dropdown1.classList.add('open');
   dropdownArray1.forEach(dropdown1 => {
     dropdown1.classList.remove('closed');
   });
});

inputField1.addEventListener('blur', () => {
  inputField1.placeholder = inputPlaceholder1;
  dropdown1.classList.remove('open');
});

document.addEventListener('click', (evt) => {
  const isDropdown1 = dropdown1.contains(evt.target);
  const isInput1 = inputField1.contains(evt.target);
  if (!isDropdown1 && !isInput1) {
    dropdown1.classList.remove('open');
  }
});
