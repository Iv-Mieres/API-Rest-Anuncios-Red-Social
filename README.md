
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
  >  ## <sub> [Enlace a video de Youtube explicando parte del funcionamiento de la API](https://youtu.be/0SE7SZPPLlM) </sub>
  
  
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
    <img width="40%" src="https://user-images.githubusercontent.com/103857812/216205026-db6c2d95-8b34-4bcb-b84a-74af54399d73.png"</img>
</div>        



>El diagrama de clase UML muestra el uso de herencia siendo las clases `Musico` `Banda` quienes heredan de la clase abstracta `PerfilUsuario`.
En la clase Usuario se puede ver las diferentes relaciones entre Musico, Banda y Role las cuales tiene una realción de composición. Para que la herencia
tuviera efecto y haya podido ser reflejada en la base de datos fué necesario agregar una anotación especifica de Jpa. Además se hizo uso de la inteface UserDetails,
implementada por la clase Usuario para que Spring Security se pueda encargar de controlar el inicio de session, los roles de usuario y el acceso a los diferentes endpoints. Támbien se utiliza el metodo `isEnable()` para eliminar un usuario de forma lógica sin necesidad de borrar sus datos en la BD.

 ## Diagrama ER
 
<div align="left">
    <img width="40%" src="https://user-images.githubusercontent.com/103857812/216206695-8bd73350-500a-4c72-87f0-754f72e22ccc.png"</img>
</div> 

>Para la creación del modelado se utilizó `Hibernate` `JPA` implementando las diferentes annotations para la creación y realción de tablas. Las relaciones aplicadas fueron tres. Un 1 a 1 para la relación entre Usuario - Musico y Usuario - Banda. Un 1 a N para las relaciones entre Musico - Publicacion y Banda - Publicacion y un N a N para la relación Usuario - Role. 

### Nota
> Al levantar el proyecto por pirmera vez asegurarse de revisar la configuración del `application.properties`  y de mantener `spring.sql.init.mode` en "always" ya que esto hace que el fichero `data.sql` ingrese en la BD, de forma automatica, los roles necesarios al inicio del proyecto. 








## README EN CONSTRUCCIÓN . . .
  
 


