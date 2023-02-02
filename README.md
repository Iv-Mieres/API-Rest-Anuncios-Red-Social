
<div align="center">
    <img width="60%" src="https://user-images.githubusercontent.com/103857812/215369878-abcfc375-fa17-4320-9e78-208166311015.png"</img>
</div>

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
  
  
  # Información Tecnica del proyecto
  
  ### <sub> - Tecnologias aplicadas - </sub>
  
  - Java 17
  - Spring Boot 2
  - Spring Security (*basic auth*)
  - jpa/Hibernate
  - MySql
  
  ### <sub> - Herramientas utilizadas - </sub>

  - IntelliJ Idea / Spring Tool Suite 4 - IDE
  - MySql Workbench
  - Postman
  - Swagger

  ## Diagrama UML 
  
<div align="left">
    <img width="60%" src="https://user-images.githubusercontent.com/103857812/216205026-db6c2d95-8b34-4bcb-b84a-74af54399d73.png"</img>
</div>        



>El diagrama de clase UML muestra el uso de herencia en las tablas `Musico` `Banda` siendo la tabla `PerfilUsuario` la clase abstracta "padre". Para que la herencia
tuviera efecto y haya podido ser reflejada en la base de datos fué necesario agregar una anotación especifica de Jpa. Además se hizo uso de la inteface UserDetails,
implementada por la clase Usuario para que Spring Security se pueda encargar de controlar el inicio de session, los roles de usuario y el acceso a los diferentes endpoints. Támbien se utiliza el metodo `isEnable()` para eliminar un usuario de forma lógica sin necesidad de borrar sus datos en la BD.

 ## Diagrama ER
 
<div align="left">
    <img width="60%" src="https://user-images.githubusercontent.com/103857812/216206695-8bd73350-500a-4c72-87f0-754f72e22ccc.png"</img>
</div> 

>Para la creación del modelado se utilizó `Hibernate` `JPA` implementando las diferentes annotations para la creación y realción de tablas. Como por ejemplo:
>@Entity, @JpaRepository, @ManyToMany, etc. Támbien se utiizó @Id Y @GeneratedValue para que sea Hibernate quien se encargue de asignar valor a los diferentes
>id que poseen cada tabla.


[<img src="https://img.youtube.com/vi/0SE7SZPPLlM/maxresdefault.jpg" width="40%">](https://youtu.be/0SE7SZPPLlM)


<video src="https://user-images.githubusercontent.com/(https://youtu.be/0SE7SZPPLlM"></video>

## README EN CONSTRUCCIÓN . . .
  
 


