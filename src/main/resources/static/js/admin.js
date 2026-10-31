const radios = document.querySelectorAll('input[name="period"]');
const todayTable = document.getElementById('today-table');
const monthTable = document.getElementById('month-table');

radios.forEach(radio => {
	radio.addEventListener('change', () => {
		const isToday = radio.value === 'today';
		
		todayTable.hidden = !isToday;
		monthTable.hidden = isToday;
	});
});