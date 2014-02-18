'use strict';

var app = {}; // create namespace for our app

// --------------
// Models
// --------------
app.Todo = Backbone.Model.extend({
	defaults : {
		task : '',
		complete : false,
		creationDate : new Date()
	},
	toggle : function(callback) {
		this.save({
			complete : !this.get('complete')
		}, callback);
	}
});

// --------------
// Collections
// --------------
app.TodoList = Backbone.Collection.extend({
	model : app.Todo,
	url : 'http://localhost:9000/todolist/api/todos'
});

// instance of the Collection
app.todoList = new app.TodoList();

// -------------
// Views
// -------------

// renders individual todo items list (div)
app.TodoView = Backbone.View.extend({
	tagName : 'div',
	template : _.template($('#bs-item-template').html()),
	render : function() {
		var displayTask = this.model.get('task');
		var complete = this.model.get('complete');
		var creationDate = new Date(this.model.get('creationDate'))
		
		this.$el.html(this.template({task:displayTask,
			                         dateStr:creationDate.getDate()+'/'+(creationDate.getMonth()+1)+'/'+creationDate.getFullYear(),
			                         complete:this.model.get('complete')}));
		this.input = this.$('.edit');
		return this; // enable chained calls
	},
	initialize : function() {
		this.model.on('change', this.render, this);
		this.model.on('destroy', this.remove, this);
	},
	events : {
		'keypress .edit' : 'updateOnEnter',
		'exit .edit' : 'close',
		'blur .edit' : 'close',
		'click .destroy' : 'destroy',
		'click .toggle' : 'toggle'
	},
	close : function() {
		var value = this.input.val().trim();
		console.log('close');
		if (value && value != this.model.get('task')) {
			this.model.save({task : value }, {success:function(){console.log('close worked')}});
		}
	},
	updateOnEnter : function(e) {
		if (e.which == 13) {
			this.close();
		}
	},
	destroy : function(e) {
		var that = this;
		this.model.destroy({success:function(){console.log('Yay destroy worked!')},
			                failure:function(){console.log('failure to destroy')}});
	},
	toggle : function(e) {
		var that =this;
		this.model.toggle({
			success : function() {
				that.$el.removeClass('has-error');
			},
			failure : function() {
				that.$el.addClass('has-error');
			}
		});
	}
});

// renders the full list of todo items calling TodoView for each one.
app.AppView = Backbone.View.extend({
	el : '#todoapp',
	initialize : function() {
		this.input = this.$('#new-todo');
		// when new elements are added to the collection render then with addOne
		app.todoList.on('add', this.addOne, this);
		app.todoList.on('reset', this.addAll, this);
		app.todoList.fetch({
			reset : true
		}); // Loads list from local storage
	},
	events : {
		'keypress #new-todo' : 'createTodoOnEnter',

	},
	createTodoOnEnter : function(e) {
		if (e.which !== 13 || !this.input.val().trim()) { // ENTER_KEY = 13
			return;
		}
		app.todoList.create(this.newAttributes());
		this.input.val(''); // clean input box
	},
	addOne : function(todo) {
		var view = new app.TodoView({
			model : todo
		});
		$('#todo-list').append(view.render().el);
	},
	addAll : function() {
		this.$('#todo-list').html(''); // clean the todo list
		app.todoList.each(this.addOne, this);
	},
	newAttributes : function() {
		return {
			task : this.input.val().trim(),
			complete : false
		}
	}
});

// --------------
// Initializers
// --------------

app.appView = new app.AppView();