
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::: API RESTful USUARIOS ::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


POST   http://localhost:8080/users
GET    http://localhost:8080/users
PUT    http://localhost:8080/users/{uuid}
PATCH  http://localhost:8080/users/{uuid}
DELETE http://localhost:8080/users/{uuid}


INSTRUCCIONES:::

1.- Realizar "clean" e "install" via Maven.

2.- Levantar el proyecto.

3.- Realizar un POST a la siguiente URL "http://localhost:8080/users" via Postman para poder dar de alta el usuario, con el JSON en el Body: 	

	{
	  "name": "Juan Rodriguez",
	  "email": "juan@rodriguez.org",
	  "password": "usernamed$omain1com",
	  "phones": [
	    {
	      "number": "1234567",
	      "citycode": "1",
	      "countrycode": "57"
	    }
	  ]
	}

	Se realizan tres validaciones:
	 - Correo ya existe en la BBDD
	 - Formato del Correo
	 - Formato de la contraseña
	
	En el caso que alguna de esas calidaciones arrojara resultados negativos se devuelve un JSON de detalle con el error:
	
	 - "El correo ya registrado" en el caso en que el correo ya existe en la BBDD.
	 - "El formato del correo no es correcto" en el caso en que el formato del correo no sea el correcto. 
	 - "El formato de la clave no es correcto" en el caso en que el formato de la contraseña no sea el correcto.
	
	
	Se genera y se guarda automáticamente el token de seguridad del usuario devolviendolo en el JSON del usuario creado:	

	{
	    "id": "8afc08ae-5ee4-4f96-81bb-162fef0d7ec2",
	    "name": "Juan Rodriguez",
	    "email": "juan@rodriguez.org",
	    "password": "usernamed$omain1com",
	    "creationDate": "2025-04-30T14:18:03.416+00:00",
	    "modificationDate": "2025-04-30T14:18:03.416+00:00",
	    "lastLoginDate": "2025-04-30T14:18:03.416+00:00",
	    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM",
	    "active": true,
	    "phones": [
	        {
	            "id": 1,
	            "number": 1234567,
	            "citycode": 1,
	            "countrycode": 57
	        }
	    ]
	}

	Dicho Token tiene que ser configurado en la solapa de "Authorization", Type "Bearer Token" para poder acceder a los demas endpoints.

4.- Realizar un GET via Postman a la siguiente URL "http://localhost:8080/users" para poder visualizar cuantos usuarios estan dados de alta, 
	antes se tiene que setear el token en la solapa de Authorization.

	Va a retornar el siguiente JSON como respuesta: 

	[
	    {
	        "id": "8afc08ae-5ee4-4f96-81bb-162fef0d7ec2",
	        "name": "Juan Rodriguez",
	        "email": "juan@rodriguez.org",
	        "password": "usernamed$omain1com",
	        "creationDate": "2025-04-30T14:18:03.416+00:00",
	        "modificationDate": "2025-04-30T14:18:03.416+00:00",
	        "lastLoginDate": "2025-04-30T14:18:03.416+00:00",
	        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM",
	        "active": true,
	        "phones": [
	            {
	                "id": 1,
	                "number": 1234567,
	                "citycode": 1,
	                "countrycode": 57
	            }
	        ]
	    }
	]

5.- Realizar un PUT via Postman a la siguiente URL "http://localhost:8080/users/{uuid}" para poder modificar algun dato del usuario, 
	antes se tiene que setear el token en la solapa de Authorization.
	El String "{uuid}" se tiene que reemplazar con el UUID de el usuario que se quiere hacer la actualizacion.
	Tomando el ejemplo anterior quedaria de la siguiente manera: "http://localhost:8080/users/8afc08ae-5ee4-4f96-81bb-162fef0d7ec2".
	Agregar el siguiente JSON al Body de la peticion para modificar el correo:


	{
		"name": "Juan Rodriguez",
		"email": "juan@rodriguez.com",
		"phones": [
		        {
		        "number": "7654321",
		        "citycode": "2",
		        "countrycode": "58"
		        }
		    ]
	}
	
	Va a retornar el siguiente JSON como respuesta con el correo modificado:
	
	{
	    "id": "8afc08ae-5ee4-4f96-81bb-162fef0d7ec2",
	    "name": "Juan Rodriguez",
	    "email": "juan@rodriguez.com",
	    "password": "usernamed$omain1com",
	    "creationDate": "2025-04-30T14:18:03.416+00:00",
	    "modificationDate": "2025-04-30T14:22:45.044+00:00",
	    "lastLoginDate": "2025-04-30T14:18:03.416+00:00",
	    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM",
	    "active": true,
	    "phones": [
	        {
	            "id": 2,
	            "number": 7654321,
	            "citycode": 2,
	            "countrycode": 58
	        }
	    ]
	}
	
6.- Realizar un PATCH via Postman a la siguiente URL "http://localhost:8080/users/{uuid}" para poder modificar solo los telefonos asociados al mismo, 
	antes se tiene que setear el token en la solapa de Authorization.
	El String "{uuid}" se tiene que reemplazar con el UUID de el usuario que se quiere hacer la actualizacion.
	Tomando el ejemplo anterior quedaria de la siguiente manera: "http://localhost:8080/users/8afc08ae-5ee4-4f96-81bb-162fef0d7ec2".
	Agregar el siguiente JSON al Body de la peticion para modificar los telefonos:

	{
	  "phones": [
	    {
	      "number": "1234567",
	      "citycode": "1",
	      "countrycode": "57"
	    },
	    {
	      "number": "7654321",
	      "citycode": "2",
	      "countrycode": "75"
	    }
	  ]
	}
	
	Va a retornar el siguiente JSON como respuesta con los telefonos actualizados:

	{
	    "id": "8afc08ae-5ee4-4f96-81bb-162fef0d7ec2",
	    "name": "Juan Rodriguez",
	    "email": "juan@rodriguez.com",
	    "password": "usernamed$omain1com",
	    "creationDate": "2025-04-30T14:18:03.416+00:00",
	    "modificationDate": "2025-04-30T14:24:39.576+00:00",
	    "lastLoginDate": "2025-04-30T14:18:03.416+00:00",
	    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM",
	    "active": true,
	    "phones": [
	        {
	            "id": 3,
	            "number": 1234567,
	            "citycode": 1,
	            "countrycode": 57
	        },
	        {
	            "id": 4,
	            "number": 7654321,
	            "citycode": 2,
	            "countrycode": 75
	        }
	    ]
	}
	
7.- Realizar un DELETE via Postman a la siguiente URL "http://localhost:8080/users/{uuid}" para poder realizar una baja lógica de usuario en la BBDD , 
	antes se tiene que setear el token en la solapa de Authorization.
	El String "{uuid}" se tiene que reemplazar con el UUID de el usuario que se quiere hacer la actualizacion.
	Tomando el ejemplo anterior quedaria de la siguiente manera: "http://localhost:8080/users/8afc08ae-5ee4-4f96-81bb-162fef0d7ec2".
	
	Va a retornar el siguiente JSON como respuesta con con el flag "active" con un valor false indicando que se dio de baja:

	{
	    "id": "8afc08ae-5ee4-4f96-81bb-162fef0d7ec2",
	    "name": "Juan Rodriguez",
	    "email": "juan@rodriguez.com",
	    "password": "usernamed$omain1com",
	    "creationDate": "2025-04-30T14:18:03.416+00:00",
	    "modificationDate": "2025-04-30T14:28:08.144+00:00",
	    "lastLoginDate": "2025-04-30T14:18:03.416+00:00",
	    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKdWFuIFJvZHJpZ3VleiJ9.9OIEaF80tLW9r6_urril5-Q2VitpTjb9d6545Als0dM",
	    "active": false,
	    "phones": [
	        {
	            "id": 3,
	            "number": 1234567,
	            "citycode": 1,
	            "countrycode": 57
	        },
	        {
	            "id": 4,
	            "number": 7654321,
	            "citycode": 2,
	            "countrycode": 75
	        }
	    ]
	}