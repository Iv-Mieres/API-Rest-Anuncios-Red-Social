![encuentro musical](https://user-images.githubusercontent.com/103857812/215369878-abcfc375-fa17-4320-9e78-208166311015.png)

### Qué es Encuentro Musical?
***Encuentro Musical es un sistema de publicaciones orientado a resolver las necesidades que tienen muschos músicos y bandas a la hora de búscar nuevos
integrantes para sus proyectos personales.
 Quienes se registren en la plataforma podrán crear, consultar, editar y eliminar sus publicaciones cuando lo deseen, así como támbien, 
 cosultar las publicaciones de otros usuarios a través de un sistema de filtrado para que la búsqueda se adapte mejor a sus necesidades.
 Cada usuario tendrá la opción de elegir y crear un perfil acorde a su situación y podrá editar y eliminar su cuenta cuando lo deseé.***
 
 ### Como útilizar Encuentro Musical?
 
 Solo crea tu usuario ingresando un `Email` y un `Username` (*los cuales deberán ser únicos*) elige el perfil que desees útilizar `Músico` o `Banda`, 
 rellena el formulario con los datos necesarios y ya estarás listo para crear tu primer anuncio ó visualizar los anuncios de otros usuarios.
  >Recuerda que podrás editar tu anuncio en cualquier momento, así como tus datos de usuario.
  
  `insertar video explicativo de uso`
  
  
  # Información Tecnica del proyecto
  
  ### <sub> - Tecnologias aplicadas - </sub>
  
  - Java 17
  - Spring Boot 2
  - Spring Security (*basic auth*)
  - jpa/Hibernate
  - MySql
  
  ## Diagrama UML   
  ![Diagramadeclase](https://user-images.githubusercontent.com/103857812/215372306-cf64a8e4-b278-4df2-9b06-e0cdfd82a8c8.png)

>El diagrama de clase UML muestra el uso de herencia en las tablas `Musico` `Banda` siendo la tabla `PerfilUsuario` la clase abstracta "padre". Para que la herencia
tuviera efecto y haya podido ser reflejada en la base de datos fué necesario agregar una anotación especifica de Jpa. Además se hizo uso de la inteface UserDetails,
implementada por la clase Usuario para que Spring Security se pueda encargar de controlar el inicio de session, los roles de usuario y el acceso a los diferentes endpoints. Támbien se utiliza el metodo `isEnable()` para eliminar un usuario de forma lógica sin necesidad de borrar sus datos en la BD.

## README EN CONSTRUCCIÓN . . .
  
 


