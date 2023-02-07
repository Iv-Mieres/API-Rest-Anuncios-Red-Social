
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
  
  
  # Información Técnica del proyecto
  
  ### <sub> - Tecnologías aplicadas - </sub>
  
  - Java 17
  - Spring Boot 2
  - Maven
  - Spring Security (*basic auth*)
  - JPA/Hibernate
  - MySql
  
  ### <sub> - Herramientas utilizadas - </sub>

  - IntelliJ Idea / Spring Tool Suite 4 - IDE
  - MySql Workbench
  - Postman
  - Swagger

  ## Diagrama UML 
  
  >El diagrama de clase UML muestra el uso de herencia siendo las clases `Musico` `Banda` quienes heredan de la clase abstracta `PerfilUsuario`.
En la clase `Usuario` se puede ver las diferentes relaciones entre Musico, Banda y Role las cuales tienen una realción de composición. Además se implementó la interface UserDetails sobre la clase Usuario para que Spring Security se pueda encargar de controlar el inicio de session y el acceso a los diferentes endpoints.
  
<div align="left">
    <img width="40%" src="https://user-images.githubusercontent.com/103857812/216205026-db6c2d95-8b34-4bcb-b84a-74af54399d73.png"</img>
</div>        


 ## Diagrama ER
 
 >Para la creación del modelado se utilizó `Hibernate` `JPA` implementando las diferentes annotations para la creación y realción de tablas. Las relaciones aplicadas fueron un 1 a 1 para la relación entre Usuario - Musico y Usuario - Banda. Un 1 a N para las relaciones entre Musico - Publicacion y Banda - Publicacion y un N a N para la relación Usuario - Role.
 
<div align="left">
    <img width="40%" src="https://user-images.githubusercontent.com/103857812/216206695-8bd73350-500a-4c72-87f0-754f72e22ccc.png"</img>
</div> 

 
 
 ## Decisiones tomadas en el proyecto

 ## <sub> - application.properties - </sub>
 ```
 // Mantener esta config al iniciar el proyecto por primera vez. Luego puede desactivarse reemplazando always por never
 spring.sql.init.mode = always 
 ```
 ## <sub> - data.sql - </sub>
 ```
INSERT INTO roles VALUES(1, 'ADMIN')
INSERT INTO roles VALUES(2, 'USER')
 ```
 Se creó el fichero `data.sql` para que al levantar la aplicación por primera vez los roles "ADMIN" y "USER" sean guardados de forma automatica en la BD, de esta forma se evita el problema de no tener acceso al endpoint "crear roles". Este problema surge al securizar dicho endpoint con Spring Security  para que solo el usuario administrador ueda acceder a él y por la composición que existe entre Usuario y Role explicada en el diagrama UML. Un Usuario no puede existir sin un Role. Entonces, como creo los roles sin el usuario "ADMIN"? 

El `data.sql` se encarga de crear los roles "ADMIN" y "USER", luego se podrá crear el usuario administrador asignandolé el role "ADMIN" creado por el data.sql y de esa forma el usuario administrador se encargará de crear los roles faltantes. Sin esta configuración nunca se podria crear, de forma manual, el role "ADMIN" y por lo tanto, nunca se podria crear el Usuario "ADMIN".

 ## <sub> - Creación de multiroles - </sub>
 
 El sistema de roles está pensado para crear diferentes jerarquias. Al crear un nuevo Usuario se le asignará automaticamente el role "USER" y luego este podrá seleccionar un segundo role "BANDA" ó "MUSICO" asignandole así dos roles distintos "USER - BANDA" o "USER - MUSICO". Cada role dará acceso a diferentes funciones de la aplicación. 
 
 El usuario "ADMIN" solo tendrá un role y acceso a funciones especificas del programa.

## <sub> - Patrón Specification - </sub>

```
public Example extends JpaRepository<E, ID>, JpaSpecificationExecutor<E, ID>{}
```

 Este patrón se encarga de realizar diferentes tipos de busquedas en la base de datos utilizando Criteria API de JPA. El patrón evita tener que crear una gran cantidad de metodos en el repository ó tener que útilizar nombres de metodos como por ejemplo `findByNameAndSurnameAndUsernameAnd...()` siendo poco legible y confuso.
 
 ## <sub> - Programación Funcional - </sub>
 
 En este proyecto se útilizó lambda con el objetivo de comparar su aplicación con respecto a la programación imperativa. No encontré grandes complicaciones al implementar lambda y aplicarlo sobre el proyecto me ayudó a comprender mucho mejor esta forma de programar. En resumen, se utilizó lambda como forma de práctica y desarrollo personal. 
 
 ## Autor 
 [<sub>Ivan Mieres</sub>](https://github.com/Iv-Mieres)
 
 










  
 


