# Proyecto: Coach de Ejercicio

## 1. Descripción del proyecto

Esta aplicación **Android (Java)** permite generar **rutinas
personalizadas de ejercicio** según el deporte seleccionado por el
usuario.\
Además, cada rutina se envía automáticamente a un **flujo de
automatización en n8n**, que puede procesarla mediante un **AI Agent** y
luego enviar un **correo electrónico al usuario**.

------------------------------------------------------------------------

## 2. Instrucciones de instalación

1.  Clonar el repositorio:

    ``` bash
    git clone https://github.com/jpinedac17/proyecto.git
    ```

2.  Abrir el proyecto en **Android Studio**.\

3.  Asegurarse de tener configurado el **SDK de Android (API 21 o
    superior)**.\

4.  Verificar que las **dependencias estén sincronizadas**.\

5.  Ejecutar la aplicación en un **emulador o dispositivo físico**.

------------------------------------------------------------------------

## 3. Integración con n8n

La aplicación envía un **objeto JSON** al **webhook** configurado en
n8n.\
El flujo es el siguiente:

### Flujo del webhook

1.  **Webhook Node**:\
    Recibe los datos de la rutina desde la app.\
    Estos datos son enviados conectándose al endpoint que proporciona el
    webhook con el método **POST**, para enviarle la rutina creada en un
    JSON.

2.  **AI Agent conectado a Open Router**:\
    Recibe el JSON enviado al webhook y genera el mensaje que el usuario
    recibirá por correo, incluyendo un **mensaje motivacional** por
    completar la rutina del día.

3.  **Send a Message (Gmail)**:\
    Envía un correo electrónico con la rutina al usuario.

------------------------------------------------------------------------

### Flujo N8N en formato JSON

🔗 [Ver en
GitHub](https://github.com/jpinedac17/proyecto/blob/main/Couch%20Ejercicio.json)

------------------------------------------------------------------------

### Ejemplo de JSON enviado al webhook

``` json
{
  "nombre": "Carlos",
  "edad": 24,
  "correo": "carlos@ejemplo.com",
  "deporte": "Fútbol",
  "rutina": [
    "Calentamiento 10 min",
    "Dominadas 3x10",
    "Abdominales 3x20",
    "Estiramiento final"
  ]
}
```

------------------------------------------------------------------------

## 4. Requisitos y dependencias

### Requisitos

-   **Android Studio**
-   **SDK de Android** con **API 21+**\
-   **Conexión a Internet** para enviar datos al webhook

### Dependencias

-   **OkHttp** → Cliente HTTP para enviar solicitudes POST\
-   **Gson** → Serialización y deserialización de JSON\
-   **AndroidX AppCompat** y **Material Components** → Interfaz moderna
    de usuario

------------------------------------------------------------------------
